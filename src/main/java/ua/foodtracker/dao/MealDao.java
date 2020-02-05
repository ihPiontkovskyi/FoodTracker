package ua.foodtracker.dao;

import ua.foodtracker.entity.MealEntity;

import java.util.List;

public interface MealDao extends BaseDao<MealEntity> {
    List<MealEntity> findAll(Page page);

    long count();
}
