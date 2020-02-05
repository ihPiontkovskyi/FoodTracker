package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.domain.Meal;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.MealValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ua.foodtracker.service.utility.EntityMapper.mapMealToEntityMeal;
import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;
import static ua.foodtracker.service.utility.ServiceUtility.getNumberOfPage;

@Service
public class MealServiceImpl implements MealService {
    private static final Long ITEMS_PER_PAGE = 3L;
    private static final String INCORRECT_DATA = "incorrect.data";

    @Autowired
    private MealDao mealDao;

    @Autowired
    private MealValidator mealValidator;

    @Override
    public List<MealEntity> findAllByPage(Integer pageNumber, Integer userId) {
        return mealDao.findAll(new Page(pageNumber, ITEMS_PER_PAGE));
    }

    @Override
    public long pageCount() {
        return getNumberOfPage(mealDao.count(), ITEMS_PER_PAGE);
    }

    @Override
    public void add(Meal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            Integer id = mealDao.save(mapMealToEntityMeal(meal));
            if (id == null || id == 0) {
                mealValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            mealValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
        try {
            if (!mealDao.deleteById(Integer.parseInt(id))) {
                mealValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
            }
        } catch (NumberFormatException ex) {
            mealValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public void modify(Meal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            if (!mealDao.update(mapMealToEntityMeal(meal))) {
                mealValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public Optional<Meal> findById(String id) {
        if (id == null) {
            mealValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
        try {
            Optional<MealEntity> mealEntity = mealDao.findById(Integer.parseInt(id));
            return mealEntity.map(EntityMapper::mapEntityMealToMeal);
        } catch (NumberFormatException ex) {
            mealValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public void setLocale(Locale locale) {
        mealValidator.setLocale(locale);
    }
}
