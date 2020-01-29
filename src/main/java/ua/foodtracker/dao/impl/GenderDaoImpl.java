package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class GenderDaoImpl extends AbstractCrudDaoImpl<Gender> implements CrudDao<Gender> {

    public static final String FIND_BY_ID_QUERY = "SELECT * FROM genders WHERE id=?";
    public static final String DELETE_QUERY = "DELETE FROM genders WHERE id=?";
    public static final String INSERT_QUERY = "INSERT INTO genders VALUES(DEFAULT,?)";
    public static final String UPDATE_QUERY = "UPDATE genders SET name=? WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM genders";

    public GenderDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    protected Gender extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Gender(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public Integer save(Gender gender) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, gender.getName());
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
    public Optional<Gender> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(Gender gender) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, gender.getName());
            ps.setObject(2, gender.getId());
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
        return delete(id, DELETE_QUERY);
    }

    @Override
    public List<Gender> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }

}
