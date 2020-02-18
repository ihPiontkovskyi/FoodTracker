package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.dao.utility.ResultSetToEntityMapper.extractRecordFromResultSet;

/**
 * implementation of Record dao
 */

@Dao
public class RecordDaoImpl extends AbstractDao<RecordEntity> implements RecordDao {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM records LEFT JOIN meals ON records.meal_id=meals.id LEFT JOIN users ON records.user_id=users.id LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE records.id=?";
    private static final String FIND_BY_USER_AND_DATE_QUERY = "SELECT * FROM records LEFT JOIN meals ON records.meal_id=meals.id LEFT JOIN users ON records.user_id=users.id LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE records.user_id=? AND date=?";
    private static final String DELETE_QUERY = "DELETE FROM records WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO records VALUES (DEFAULT,?,?,?,?)";

    public RecordDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected RecordEntity extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractRecordFromResultSet(resultSet);
    }

    @Override
    public List<RecordEntity> findByUserIdAndDate(int id, LocalDate date) {
        try (PreparedStatement ps = getConnection().prepareStatement(FIND_BY_USER_AND_DATE_QUERY)) {
            ps.setObject(1, id);
            ps.setObject(2, date);
            try (ResultSet resultSet = ps.executeQuery()) {
                List<RecordEntity> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(extractFromResultSet(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, FIND_BY_USER_AND_DATE_QUERY, e));
            throw new DatabaseInteractionException(getMessage(FIND_BY_USER_AND_DATE_QUERY), e);
        }
    }

    @Override
    public Integer save(RecordEntity recordEntity) {
        return save(recordEntity, INSERT_QUERY);
    }

    @Override
    public Optional<RecordEntity> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(RecordEntity recordEntity) {
        throw new UnsupportedOperationException("update.for.records.unsupported");
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
    }

    @Override
    protected void prepareData(RecordEntity recordEntity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, recordEntity.getMealEntity().getId());
        ps.setObject(2, recordEntity.getDate());
        ps.setObject(3, recordEntity.getUserId());
        ps.setObject(4, recordEntity.getWeight());
    }

    @Override
    protected void prepareDataWithId(RecordEntity recordEntity, PreparedStatement ps) throws SQLException {
        prepareData(recordEntity, ps);
        ps.setObject(5, recordEntity.getId());
    }
}
