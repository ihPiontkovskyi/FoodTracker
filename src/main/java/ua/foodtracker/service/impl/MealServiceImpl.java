package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.MealValidatorImpl;

import java.util.List;
import java.util.stream.Collectors;

import static ua.foodtracker.service.utility.EntityMapper.mapMealToEntityMeal;
import static ua.foodtracker.service.utility.ServiceUtility.addByType;
import static ua.foodtracker.service.utility.ServiceUtility.deleteByStringId;
import static ua.foodtracker.service.utility.ServiceUtility.findByStringParam;
import static ua.foodtracker.service.utility.ServiceUtility.getNumberOfPage;
import static ua.foodtracker.service.utility.ServiceUtility.modifyByType;

@Service
public class MealServiceImpl implements MealService {
    private static final Long ITEMS_PER_PAGE = 10L;

    @Autowired
    private MealDao mealDao;

    @Autowired
    private MealValidatorImpl mealValidator;

    @Override
    public List<Meal> findAllByPage(String pageNumber) {
        return findByStringParam(pageNumber, number -> {
            if (number < 1 || number > pageCount()) {
                number = 1;
            }
            return mealDao.findAll(new Page(number, ITEMS_PER_PAGE)).stream()
                    .map(EntityMapper::mapEntityMealToMeal)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public long pageCount() {
        return getNumberOfPage(mealDao.count(), ITEMS_PER_PAGE);
    }

    @Override
    public void add(Meal meal) {
        addByType(meal, mealValidator, obj -> mealDao.save(mapMealToEntityMeal(recountData(obj))));
    }

    @Override
    public void delete(String id) {
        deleteByStringId(id, intId -> mealDao.deleteById(intId));
    }

    @Override
    public void modify(Meal meal) {
        modifyByType(meal, mealValidator, obj -> mealDao.update(mapMealToEntityMeal(obj)));
    }

    @Override
    public Meal findById(String id) {
        return findByStringParam(id, intId -> mealDao.findById(intId)
                .map(EntityMapper::mapEntityMealToMeal))
                .orElseThrow(() -> new IncorrectDataException("meal.not.found"));
    }

    @Override
    public List<Meal> findAllByNameStartWith(String term) {
        return mealDao.findAllByNameStartWith(term).stream()
                .map(EntityMapper::mapEntityMealToMeal)
                .collect(Collectors.toList());
    }

    private Meal recountData(Meal meal) {
        double coefficient = 100.0 / meal.getWeight();
        return Meal.builder()
                .withCarbohydrates((int) (meal.getCarbohydrate() * coefficient))
                .withFat((int) (meal.getFat() * coefficient))
                .withProtein((int) (meal.getProtein() * coefficient))
                .withName(meal.getName())
                .withUser(meal.getUser())
                .withWater((int) (meal.getWater() * coefficient))
                .withWeight(100)
                .withId(meal.getId())
                .build();
    }
}
