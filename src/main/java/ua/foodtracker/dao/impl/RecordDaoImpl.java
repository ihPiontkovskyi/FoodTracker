package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Record;
import ua.foodtracker.exception.DatabaseInteractionException;
import ua.foodtracker.utility.Query;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.utility.EntityMapper.extractRecordFromResultSet;

@Dao
public class RecordDaoImpl extends AbstractDaoImpl<Record> implements RecordDao {
    private static final String FIND_BY_USER_AND_DATE_QUERY_KEY = "records.find.by.user.and.date";
    private static final String FIND_BY_ID_QUERY_KEY = "records.find.by.id";
    private static final String DELETE_QUERY_KEY = "records.delete.by.id";
    private static final String INSERT_QUERY_KEY = "records.insert";
    private static final String UPDATE_QUERY_KEY = "records.update";

    public RecordDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected Record extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractRecordFromResultSet(resultSet);
    }

    @Override
    public List<Record> findByUserIdAndDate(int id, Date date) {
        String query = Query.getQuery(FIND_BY_USER_AND_DATE_QUERY_KEY);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
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
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
    }

    @Override
    public Integer save(Record record) {
        return save(record, INSERT_QUERY_KEY);
    }

    @Override
    public Optional<Record> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY_KEY);
    }

    @Override
    public boolean update(Record record) {
        return update(record, UPDATE_QUERY_KEY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY_KEY);
    }

    @Override
    protected void prepareData(Record record, PreparedStatement ps) throws SQLException {
        ps.setObject(1, record.getMeal().getId());
        ps.setObject(2, record.getDate());
    }

    @Override
    protected void prepareDataWithId(Record record, PreparedStatement ps) throws SQLException {
        prepareData(record,ps);
        ps.setObject(3,record.getId());
    }
}
