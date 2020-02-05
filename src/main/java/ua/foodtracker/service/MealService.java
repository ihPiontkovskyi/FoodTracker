package ua.foodtracker.service;

import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.service.domain.Meal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MealService {
    List<MealEntity> findAllByPage(Integer pageNumber, Integer userId);

    long pageCount();

    void add(Meal meal);

    void delete(String id);

    void modify(Meal meal);

    Optional<Meal> findById(String id);

    void setLocale(Locale locale);
}
