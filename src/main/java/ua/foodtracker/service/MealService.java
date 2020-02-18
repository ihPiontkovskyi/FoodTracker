package ua.foodtracker.service;

import ua.foodtracker.domain.Meal;

import java.util.List;
import java.util.Locale;

public interface MealService {
    List<Meal> findAllByPage(String pageNumber);

    long pageCount();

    void add(Meal meal);

    void delete(String id);

    void modify(Meal meal);

    Meal findById(String id);

    List<Meal> findAllByNameStartWith(String term);
}
