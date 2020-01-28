package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudPageableDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MealDaoImpl extends AbstractCrudDaoImpl<Meal> implements CrudPageableDao<Meal> {

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM meals WHERE id=?";
    public static final String DELETE_QUERY = "DELETE FROM meals WHERE id=?";
    public static final String INSERT_QUERY = "INSERT INTO meals VALUES (DEFAULT,?,?,?,?,?,?,?)";
    public static final String UPDATE_QUERY = "UPDATE meals SET user_id=?,fat=?,protein=?,carbohydrate=?,water=?,weight=?,name=? WHERE id=?";
    public static final String SELECT_PAGE_QUERY = "SELECT * FROM meals LIMIT ? OFFSET ?";
    public static final String COUNT_RECORD_QUERY = "SELECT COUNT(1) FROM meals";

    public MealDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public List<Meal> findAll(Page page) {
        return findAll(SELECT_PAGE_QUERY, page);
    }

    @Override
    public long count() {
        return count(COUNT_RECORD_QUERY);
    }

    @Override
    protected Meal extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Meal.builder().
                withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withCarbohydrates(resultSet.getInt("carbohydrate"))
                .withFat(resultSet.getInt("fat"))
                .withProtein(resultSet.getInt("protein"))
                .withWater(resultSet.getInt("water"))
                .withWeight(resultSet.getInt("weight"))
                .withUserId(resultSet.getInt("user_id"))
                .build();
    }

    @Override
    public boolean save(Meal meal) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            ps.setObject(1, meal.getUserId());
            ps.setObject(2, meal.getFat());
            ps.setObject(3, meal.getProtein());
            ps.setObject(4, meal.getCarbohydrates());
            ps.setObject(5, meal.getWater());
            ps.setObject(6, meal.getWeight());
            ps.setObject(7, meal.getName());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, INSERT_QUERY, e));
            throw new DataAccessException(getMessage(INSERT_QUERY), e);
        }
        return false;
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        return findByParam(id, SELECT_BY_ID_QUERY);
    }

    @Override
    public boolean update(Meal meal) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, meal.getUserId());
            ps.setObject(2, meal.getFat());
            ps.setObject(3, meal.getProtein());
            ps.setObject(4, meal.getCarbohydrates());
            ps.setObject(5, meal.getWater());
            ps.setObject(6, meal.getWeight());
            ps.setObject(7, meal.getName());
            ps.setObject(8, meal.getId());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, UPDATE_QUERY, e));
            throw new DataAccessException(getMessage(UPDATE_QUERY), e);
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
    }
}
