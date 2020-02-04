package ua.foodtracker.service;

import ua.foodtracker.entity.Meal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MealService {
    List<Meal> findAllByPage(String pageNumber, Integer userId);

    Long pageCount();

    void add(Meal meal);

    void delete(Integer id);

    void modify(Meal meal);

    Optional<Meal> findById(Integer id);

    void setLocale(Locale locale);
}
