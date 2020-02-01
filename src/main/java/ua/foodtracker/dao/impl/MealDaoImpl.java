package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Meal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.utility.EntityMapper.extractMealFromResultSet;

@Dao
public class MealDaoImpl extends AbstractDaoImpl<Meal> implements MealDao {
    private static final String FIND_PAGE_QUERY_KEY = "meals.find.page";
    private static final String COUNT_RECORD_QUERY_KEY = "meals.get.count";
    private static final String FIND_BY_ID_QUERY_KEY = "meals.find.by.id";
    private static final String DELETE_QUERY_KEY = "meals.delete.by.id";
    private static final String INSERT_QUERY_KEY = "meals.insert";
    private static final String UPDATE_QUERY_KEY = "meals.update";

    public MealDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected Meal extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractMealFromResultSet(resultSet);
    }

    @Override
    public List<Meal> findAll(Page page) {
        return findAll(FIND_PAGE_QUERY_KEY, page);
    }

    @Override
    public Long count() {
        return count(COUNT_RECORD_QUERY_KEY);
    }

    @Override
    public Integer save(Meal meal) {
        return save(meal, INSERT_QUERY_KEY);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY_KEY);
    }

    @Override
    public boolean update(Meal meal) {
        return update(meal, UPDATE_QUERY_KEY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY_KEY);
    }

    @Override
    protected void prepareData(Meal meal, PreparedStatement ps) throws SQLException {
        ps.setObject(1, meal.getUser().getId());
        ps.setObject(2, meal.getFat());
        ps.setObject(3, meal.getProtein());
        ps.setObject(4, meal.getCarbohydrate());
        ps.setObject(5, meal.getWater());
        ps.setObject(6, meal.getWeight());
        ps.setObject(7, meal.getName());
    }

    @Override
    protected void prepareDataWithId(Meal meal, PreparedStatement ps) throws SQLException {
        prepareData(meal, ps);
        ps.setObject(8, meal.getId());
    }
}
