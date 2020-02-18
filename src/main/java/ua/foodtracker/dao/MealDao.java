package ua.foodtracker.dao;

import ua.foodtracker.entity.MealEntity;

import java.util.List;

/**
 * Meal dao class which contain methods interaction with meals table in DB.
 */
public interface MealDao extends BaseDao<MealEntity> {
    List<MealEntity> findAll(Page page);

    List<MealEntity> findAllByNameStartWith(String term);

    long count();
}
