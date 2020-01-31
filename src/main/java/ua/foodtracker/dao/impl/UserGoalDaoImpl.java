package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.BaseDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.UserGoal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static ua.foodtracker.utility.EntityMapper.extractUserGoalsFromResultSet;

public class UserGoalDaoImpl extends AbstractDaoImpl<UserGoal> implements BaseDao<UserGoal> {
    private static final String FIND_BY_ID_QUERY = "users.goals.find.by.id";
    private static final String DELETE_QUERY = "users.goals.delete.by.id";
    private static final String INSERT_QUERY = "users.goals.insert";
    private static final String UPDATE_QUERY = "users.goals.update";

    public UserGoalDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    protected UserGoal extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractUserGoalsFromResultSet(resultSet);
    }

    @Override
    public Integer save(UserGoal userGoal) {
        return save(userGoal, INSERT_QUERY);
    }

    @Override
    public Optional<UserGoal> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(UserGoal userGoal) {
        return update(userGoal, UPDATE_QUERY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
    }

    @Override
    protected void prepareData(UserGoal userGoal, PreparedStatement ps) throws SQLException {
        ps.setObject(1, userGoal.getDailyEnergyGoal());
        ps.setObject(2, userGoal.getDailyFatGoal());
        ps.setObject(3, userGoal.getDailyProteinGoal());
        ps.setObject(4, userGoal.getDailyWaterGoal());
        ps.setObject(5, userGoal.getDailyCarbohydrateGoal());
    }

    @Override
    protected void prepareDataWithId(UserGoal userGoal, PreparedStatement ps) throws SQLException {
        prepareData(userGoal, ps);
        ps.setObject(6, userGoal.getId());
    }
}
