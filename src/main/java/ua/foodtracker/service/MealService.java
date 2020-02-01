package ua.foodtracker.service;

import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.Meal;

import java.util.List;
import java.util.Optional;

public interface MealService {
    List<Meal> getPage(Integer pageNumber, Integer userId);

    Long getPageCount();

    boolean add(Meal meal);

    boolean delete(Meal meal);

    boolean modify(Meal meal);

    Optional<Meal> findById(Integer id);
}
