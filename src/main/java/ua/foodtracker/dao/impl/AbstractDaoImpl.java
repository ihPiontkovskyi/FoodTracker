package ua.foodtracker.dao.impl;

import org.apache.log4j.Logger;
import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.foodtracker.utility.Query.getQuery;

/**
 * Provides a base functionality for all dao.
 */
public abstract class AbstractDaoImpl<E> implements CrudDao<E> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDaoImpl.class);

    protected static final String ERROR_MESSAGE = "Cannot handle sql ['%s']; Message:%s";

    private final ConnectionHolder connectionHolder;

    /**
     * Creates a new dao.
     *
     * @param connectionHolder connection holder
     */
    public AbstractDaoImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    /**
     * Gets a connection from connection holder.
     *
     * @return connection from connection holder
     */
    protected Connection getConnection() {
        return connectionHolder.get();
    }

    protected boolean delete(Integer id, String queryKey) {
        String query = getQuery(queryKey);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setObject(1, id);
            return id != 0 && ps.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
    }

    protected Integer save(E entity, String queryKey) {
        String query = getQuery(queryKey);
        try (PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareData(entity, ps);
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
        throw new DatabaseInteractionException(getMessage(query));
    }

    protected boolean update(E entity, String queryKey) {
        String query = getQuery(queryKey);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            prepareWithId(entity, ps);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
        return false;
    }

    protected <P> Optional<E> findByParam(P param, String queryKey) {
        String query = getQuery(queryKey);
        try (final PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setObject(1, param);
            try (final ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
        return Optional.empty();
    }

    protected abstract E extractFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void prepareData(E entity, PreparedStatement ps) throws SQLException;

    protected abstract void prepareWithId(E entity, PreparedStatement ps) throws SQLException;

    protected List<E> findAll(String queryKey, Page page) {
        String query = getQuery(queryKey);
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
            throw new DatabaseInteractionException(getMessage(query), e);
        }
    }

    protected Long count(String queryKey) {
        String query = getQuery(queryKey);
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new DatabaseInteractionException(getMessage(query));
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
    }

    /**
     * Generates a message for {@link DatabaseInteractionException}
     *
     * @param sql query that has not been executed properly
     * @return generated message
     */
    protected static String getMessage(String sql) {
        return String.format("Cannot handle sql ['%s']", sql);
    }

}