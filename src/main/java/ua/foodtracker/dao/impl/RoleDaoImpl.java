package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Role;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class RoleDaoImpl extends AbstractCrudDaoImpl<Role> implements CrudDao<Role> {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM roles WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM roles WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO roles VALUES(DEFAULT,?)";
    private static final String UPDATE_QUERY = "UPDATE roles SET name=? WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM roles";

    public RoleDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    public Integer save(Role role) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, role.getName());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, INSERT_QUERY, e));
            throw new DataAccessException(getMessage(INSERT_QUERY), e);
        }
        throw new DataAccessException(getMessage(INSERT_QUERY));
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(Role role) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, role.getName());
            ps.setObject(2, role.getId());
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
        return delete(id, DELETE_BY_ID_QUERY);
    }

    @Override
    public List<Role> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }

    @Override
    protected Role extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Role(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
