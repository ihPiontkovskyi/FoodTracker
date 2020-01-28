package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.UserGoal;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserGoalDaoImpl extends AbstractCrudDaoImpl<UserGoal> implements CrudDao<UserGoal> {

    private static final String FIND_BY_ID = "SELECT * FROM user_goals WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM user_goals WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO user_goals VALUES(DEFAULT,?,?,?,?,?)";
    public static final String UPDATE_QUERY = "UPDATE user_goals SET daily_energy=?,daily_fat=?,daily_protein=?,daily_water=?,daily_carbohydrate=? WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM lifestyles";

    public UserGoalDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    protected UserGoal extractFromResultSet(ResultSet resultSet) throws SQLException {
        return UserGoal.builder()
                .withId(resultSet.getInt("id"))
                .withDailyEnergyGoal(resultSet.getInt("daily_energy"))
                .withDailyCarbohydrateGoal(resultSet.getInt("daily_carbohydrate"))
                .withDailyFatGoal(resultSet.getInt("daily_fat"))
                .withDailyWaterGoal(resultSet.getInt("daily_water"))
                .withDailyProteinGoal(resultSet.getInt("daily_protein"))
                .build();
    }

    @Override
    public boolean save(UserGoal entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            ps.setObject(1, entity.getDailyEnergyGoal());
            ps.setObject(2, entity.getDailyFatGoal());
            ps.setObject(3, entity.getDailyProteinGoal());
            ps.setObject(4, entity.getDailyWaterGoal());
            ps.setObject(5, entity.getDailyCarbohydrateGoal());
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
    public Optional<UserGoal> findById(Integer id) {
        return findByParam(id, FIND_BY_ID, PARAM_SETTER);
    }

    @Override
    public boolean update(UserGoal entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, entity.getDailyEnergyGoal());
            ps.setObject(2, entity.getDailyFatGoal());
            ps.setObject(3, entity.getDailyProteinGoal());
            ps.setObject(4, entity.getDailyWaterGoal());
            ps.setObject(5, entity.getDailyCarbohydrateGoal());
            ps.setObject(6, entity.getId());
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
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_BY_ID);
    }

    @Override
    public List<UserGoal> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }
}
