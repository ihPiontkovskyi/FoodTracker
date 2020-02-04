package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.entity.Meal;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.raw.type.entity.RawMeal;
import ua.foodtracker.service.MealService;
import ua.foodtracker.validator.impl.MealValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ua.foodtracker.dao.utility.EntityMapper.mapRawMealToEntityMeal;
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
    public List<Meal> findAllByPage(Integer pageNumber, Integer userId) {
        return mealDao.findAll(new Page(pageNumber, ITEMS_PER_PAGE));
    }

    @Override
    public Long pageCount() {
        return getNumberOfPage(mealDao.count(),ITEMS_PER_PAGE);
    }

    @Override
    public void add(RawMeal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            Integer id = mealDao.save(mapRawMealToEntityMeal(meal));
            if (id != null && id != 0) {
                mealValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public void delete(Integer id) {
        if (mealDao.deleteById(id)) {
            mealValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public void modify(RawMeal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            if (mealDao.update(mapRawMealToEntityMeal(meal))) {
                mealValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(), mealValidator.getLocale()));
        }
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        return mealDao.findById(id);
    }

    @Override
    public void setLocale(Locale locale) {
        mealValidator.setLocale(locale);
    }
}
