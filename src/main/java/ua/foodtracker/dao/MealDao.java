package ua.foodtracker.dao;

import ua.foodtracker.entity.Meal;

import java.util.List;

public interface MealDao extends BaseDao<Meal> {
    List<Meal> findAll(Page page);

    Long count();
}
