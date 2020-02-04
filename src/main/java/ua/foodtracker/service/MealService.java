package ua.foodtracker.service;

import ua.foodtracker.dao.entity.Meal;
import ua.foodtracker.raw.type.entity.RawMeal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MealService {
    List<Meal> findAllByPage(String pageNumber, Integer userId);

    Long pageCount();

    void add(RawMeal meal);

    void delete(Integer id);

    void modify(RawMeal meal);

    Optional<Meal> findById(Integer id);

    void setLocale(Locale locale);
}
