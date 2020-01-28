package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LifestyleDaoImpl extends AbstractCrudDaoImpl<Lifestyle> implements CrudDao<Lifestyle> {

    private static final String FIND_BY_ID = "SELECT * FROM lifestyles WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM lifestyles WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO lifestyles VALUES(DEFAULT,?,?,?)";
    public static final String UPDATE_QUERY = "UPDATE lifestyles SET name=?,description=?,energy_coefficient=? WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM lifestyles";

    public LifestyleDaoImpl(ConnectionHolder holder) {
        super(holder);
    }

    @Override
    protected Lifestyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Lifestyle.builder()
                .withId(resultSet.getInt("id"))
                .withName(resultSet.getString("name"))
                .withDescription(resultSet.getString("description"))
                .withEnergyCoefficient(resultSet.getDouble("energy_coefficient"))
                .build();
    }

    @Override
    public boolean save(Lifestyle entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            ps.setObject(1, entity.getName());
            ps.setObject(2, entity.getDescription());
            ps.setObject(3, entity.getEnergyCoefficient());
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
    public Optional<Lifestyle> findById(Integer id) {
        return findByParam(id, FIND_BY_ID, PARAM_SETTER);
    }

    @Override
    public boolean update(Lifestyle entity) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, entity.getName());
            ps.setObject(2, entity.getDescription());
            ps.setObject(3, entity.getEnergyCoefficient());
            ps.setObject(4, entity.getId());
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
    public List<Lifestyle> findAll() {
        return findAll(SELECT_ALL_QUERY);
    }
}
