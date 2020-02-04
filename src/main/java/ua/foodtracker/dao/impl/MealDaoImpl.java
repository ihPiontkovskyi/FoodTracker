package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.entity.Meal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.dao.utility.EntityMapper.extractMealFromResultSet;

@Dao
public class MealDaoImpl extends AbstractDaoImpl<Meal> implements MealDao {
    private static final String FIND_PAGE_QUERY = "SELECT * FROM meals LEFT JOIN users ON meals.user_id=users.id LEFT JOIN user_goals ON users.id=user_goals.id LIMIT ? OFFSET ?";
    private static final String COUNT_RECORD_QUERY = "SELECT COUNT(1) FROM meals";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM meals LEFT JOIN users ON meals.user_id=users.id LEFT JOIN user_goals ON users.id=user_goals.id WHERE meals.id=?";
    private static final String DELETE_QUERY = "DELETE FROM meals WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO meals VALUES (DEFAULT,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY_KEY = "UPDATE meals SET user_id=?,fat=?,protein=?,carbohydrate=?,water=?,weight=?,name=? WHERE id=?";

    public MealDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected Meal extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractMealFromResultSet(resultSet);
    }

    @Override
    public List<Meal> findAll(Page page) {
        return findAll(FIND_PAGE_QUERY, page);
    }

    @Override
    public long count() {
        return count(COUNT_RECORD_QUERY);
    }

    @Override
    public Integer save(Meal meal) {
        return save(meal, INSERT_QUERY);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(Meal meal) {
        return update(meal, UPDATE_QUERY_KEY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
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
