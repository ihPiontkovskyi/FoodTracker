package ua.foodtracker.dao.impl;

import org.apache.log4j.Logger;
import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.exception.DataAccessException;
import ua.foodtracker.exception.PrepareStatementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractCrudDaoImpl.class);
    protected static final String ERROR_MESSAGE = "Cannot handle sql ['%s']; Message:%s";
    protected static final String STATEMENT_ERROR_MESSAGE = "Cannot prepare statement! Message:%s";
    protected static final BiConsumer<PreparedStatement, Object> PARAM_SETTER = (preparedStatement, obj) -> {
        try {
            preparedStatement.setObject(1, obj);
        } catch (SQLException e) {
            LOGGER.warn(String.format(STATEMENT_ERROR_MESSAGE, e));
            throw new PrepareStatementException(e);
        }
    };

    private final ConnectionHolder connectionHolder;

    public AbstractCrudDaoImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    protected Connection getConnection() {
        return connectionHolder.get();
    }

    protected boolean delete(Integer id, String sql) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            PARAM_SETTER.accept(ps, id);
            return id != 0 && ps.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, sql, e));
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    protected <P> Optional<E> findByParam(P param, String query, BiConsumer<PreparedStatement, P> designatedParamSetter) {
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            designatedParamSetter.accept(preparedStatement, param);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DataAccessException(getMessage(query), e);
        }
        return Optional.empty();
    }

    protected abstract E extractFromResultSet(ResultSet resultSet) throws SQLException;

    protected static String getMessage(String sql) {
        return String.format("Cannot handle sql ['%s']", sql);
    }

    protected List<E> findAll(String query) {
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                List<E> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(extractFromResultSet(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DataAccessException(getMessage(query), e);
        }
    }

    protected List<E> findAll(String query, Page page) {
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setObject(1, page.getRecordNumber());
            ps.setObject(2, page.getPageNumber());
            try (ResultSet resultSet = ps.executeQuery()) {
                List<E> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(extractFromResultSet(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DataAccessException(getMessage(query), e);
        }
    }

    protected long count(String query) {
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return 0;
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DataAccessException(getMessage(query), e);
        }
    }
}
