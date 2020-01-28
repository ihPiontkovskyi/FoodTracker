package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Record;
import ua.foodtracker.exception.DataAccessException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordDaoImpl extends AbstractCrudDaoImpl<Record> implements RecordDao {

    public static final String INSERT_QUERY = "INSERT INTO records VALUES (DEFAULT,?,?,?)";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM records WHERE id=?";
    public static final String UPDATE_QUERY = "UPDATE records SET user_id=?,meal_id=?,date=? WHERE id =?";
    public static final String DELETE_QUERY = "DELETE FROM records WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM records";
    public static final String SELECT_BY_USER_ID_AND_DATE = "SELECT * FROM records WHERE user_id=? AND date=?";

    public RecordDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public List<Record> findByUserIdAndDate(int id, Date date) {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_BY_USER_ID_AND_DATE)) {
            ps.setObject(1, id);
            ps.setObject(2, date);
            try (ResultSet resultSet = ps.executeQuery()) {
                List<Record> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(extractFromResultSet(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, INSERT_QUERY, e));
            throw new DataAccessException(getMessage(INSERT_QUERY), e);
        }
    }

    @Override
    protected Record extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Record.builder()
                .withId(resultSet.getInt("id"))
                .withMeal(resultSet.getInt("meal_id"))
                .withUserId(resultSet.getInt("user_id"))
                .withDate(resultSet.getDate("date"))
                .build();
    }

    @Override
    public boolean save(Record entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            ps.setObject(1, entity.getUserId());
            ps.setObject(2, entity.getMealId());
            ps.setObject(3, entity.getDate());
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
    public Optional<Record> findById(Integer id) {
        return findByParam(id, SELECT_BY_ID_QUERY, PARAM_SETTER);
    }

    @Override
    public List<Record> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }

    @Override
    public boolean update(Record entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, entity.getUserId());
            ps.setObject(2, entity.getMealId());
            ps.setObject(3, entity.getDate());
            ps.setObject(4, entity.getId());
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
