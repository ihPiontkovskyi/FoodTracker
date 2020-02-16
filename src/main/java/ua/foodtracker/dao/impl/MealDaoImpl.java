package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.MealEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.dao.utility.ResultSetToEntityMapper.extractMealFromResultSet;

@Dao
public class MealDaoImpl extends AbstractDaoImpl<MealEntity> implements MealDao {
    private static final String FIND_PAGE_QUERY = "SELECT * FROM meals LEFT JOIN users ON meals.user_id=users.id LEFT JOIN user_goals ON users.user_goal_id=user_goals.id LIMIT ? OFFSET ?";
    private static final String COUNT_RECORD_QUERY = "SELECT COUNT(1) FROM meals";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM meals LEFT JOIN users ON meals.user_id=users.id LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE meals.id=?";
    private static final String DELETE_QUERY = "DELETE FROM meals WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO meals VALUES (DEFAULT,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY_KEY = "UPDATE meals SET user_id=?,fat=?,protein=?,carbohydrate=?,water=?,weight=?,name=? WHERE id=?";
    private static final String FIND_ALL_START_WITH_QUERY_KEY = "SELECT * FROM meals LEFT JOIN users ON meals.user_id=users.id LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE meals.name LIKE ?";

    public MealDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected MealEntity extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractMealFromResultSet(resultSet);
    }

    @Override
    public List<MealEntity> findAll(Page page) {
        return findAll(FIND_PAGE_QUERY, page);
    }

    @Override
    public List<MealEntity> findAllByNameStartWith(String term) {
        return findAllByParam(FIND_ALL_START_WITH_QUERY_KEY, "%" + term);
    }

    @Override
    public long count() {
        return count(COUNT_RECORD_QUERY);
    }

    @Override
    public Integer save(MealEntity mealEntity) {
        return save(mealEntity, INSERT_QUERY);
    }

    @Override
    public Optional<MealEntity> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(MealEntity mealEntity) {
        return update(mealEntity, UPDATE_QUERY_KEY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
    }

    @Override
    protected void prepareData(MealEntity mealEntity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, mealEntity.getUserEntity().getId());
        ps.setObject(2, mealEntity.getFat());
        ps.setObject(3, mealEntity.getProtein());
        ps.setObject(4, mealEntity.getCarbohydrate());
        ps.setObject(5, mealEntity.getWater());
        ps.setObject(6, mealEntity.getWeight());
        ps.setObject(7, mealEntity.getName());
    }

    @Override
    protected void prepareDataWithId(MealEntity mealEntity, PreparedStatement ps) throws SQLException {
        prepareData(mealEntity, ps);
        ps.setObject(8, mealEntity.getId());
    }
}
