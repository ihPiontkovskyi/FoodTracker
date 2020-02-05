package ua.foodtracker.service;

import ua.foodtracker.entity.Meal;
import ua.foodtracker.service.entity.RawMeal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MealService {
    List<Meal> findAllByPage(Integer pageNumber, Integer userId);

    long pageCount();

    void add(RawMeal meal);

    void delete(String id);

    void modify(RawMeal meal);

    Optional<Meal> findById(String id);

    void setLocale(Locale locale);
}
