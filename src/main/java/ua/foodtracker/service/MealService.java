package ua.foodtracker.service;

import ua.foodtracker.domain.Meal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MealService {
    List<Meal> findAllByPage(String pageNumber);

    long pageCount();

    void add(Meal meal);

    void delete(String id);

    void modify(Meal meal);

    Meal findById(String id);

    void setLocale(Locale locale);

    List<Meal> findAllByNameStartWith(String term);
}
