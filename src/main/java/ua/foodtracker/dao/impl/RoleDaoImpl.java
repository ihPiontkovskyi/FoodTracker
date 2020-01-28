package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Role;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoleDaoImpl extends AbstractCrudDaoImpl<Role> implements CrudDao<Role> {
    private static final String FIND_BY_ID = "SELECT * FROM roles WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM roles WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO roles VALUES(DEFAULT,?)";
    public static final String UPDATE_QUERY = "UPDATE roles SET name=? WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM roles";

    public RoleDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    protected Role extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Role(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public boolean save(Role entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            PARAM_SETTER.accept(ps, entity.getName());
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
    public Optional<Role> findById(Integer id) {
        return findByParam(id, FIND_BY_ID, PARAM_SETTER);
    }

    @Override
    public boolean update(Role entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, entity.getName());
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
        return delete(id, DELETE_BY_ID);
    }

    @Override
    public List<Role> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }
}
