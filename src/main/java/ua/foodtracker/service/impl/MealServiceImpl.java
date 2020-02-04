package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.MealService;
import ua.foodtracker.validator.impl.MealValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;
import static ua.foodtracker.service.utility.ServiceUtility.getNumberOfPage;
import static ua.foodtracker.service.utility.ServiceUtility.getPageNumberByString;

@Service
public class MealServiceImpl implements MealService {
    public static final Long ITEMS_PER_PAGE = 20L;

    @Autowired
    private MealDao mealDao;

    @Autowired
    private MealValidator mealValidator;

    @Override
    public List<Meal> findAllByPage(String pageNumber, Integer userId) {
        return mealDao.findAll(new Page(getPageNumberByString(pageNumber, pageCount()), ITEMS_PER_PAGE));
    }

    @Override
    public Long pageCount() {
        return getNumberOfPage(mealDao.count());
    }

    @Override
    public boolean add(Meal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            Integer id = mealDao.save(meal);
            return id != null && id != 0;
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(),mealValidator.getLocale()));
        }
    }

    @Override
    public boolean delete(Integer id) {
        return mealDao.deleteById(id);
    }

    @Override
    public boolean modify(Meal meal) {
        mealValidator.validate(meal);
        if (!mealValidator.hasErrors()) {
            return mealDao.update(meal);
        } else {
            throw new ValidationException(getErrorMessageByIssues(mealValidator.getMessages(),mealValidator.getLocale()));
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
