package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.User;
import ua.foodtracker.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

public class UserDaoImpl extends AbstractCrudDaoImpl<User> implements UserDao {

    public static final String SELECT_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?";
    public static final String SELECT_PAGE_QUERY = "SELECT * FROM users LIMIT ? OFFSET ?";
    public static final String COUNT_RECORD_QUERY = "SELECT COUNT(1) FROM users";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    public static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
    public static final String INSERT_QUERY = "INSERT INTO users VALUES (DEFAULT ,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_QUERY = "UPDATE users SET email=?,password=?,first_name=?,last_name=?,height=?,weight=?,birthday=?,gender_id=?,user_goal_id=?,lifestyle_id=?,role_id=? WHERE id=?";

    public UserDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParam(email, SELECT_BY_EMAIL_QUERY);
    }

    @Override
    public List<User> findAll(Page page) {
        return findAll(SELECT_PAGE_QUERY, page);
    }

    @Override
    public long count() {
        return count(COUNT_RECORD_QUERY);
    }

    @Override
    protected User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .withId(resultSet.getInt("id"))
                .withEmail(resultSet.getString("email"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withGender(resultSet.getInt("gender_id"))
                .withBirthday(resultSet.getDate("birthday"))
                .withHeight(resultSet.getInt("height"))
                .withWeight(resultSet.getInt("weight"))
                .withLifestyle(resultSet.getInt("lifestyle_id"))
                .withPassword(resultSet.getString("password"))
                .withRole(resultSet.getInt("role_id"))
                .withUserGoal(resultSet.getInt("user_goal_id"))
                .build();
    }

    @Override
    public boolean save(User user) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_QUERY)) {
            ps.setObject(1, user.getEmail());
            ps.setObject(2, hashpw(user.getPassword(), gensalt()));
            ps.setObject(3, user.getFirstName());
            ps.setObject(4, user.getLastName());
            ps.setObject(5, user.getHeight());
            ps.setObject(6, user.getWeight());
            ps.setObject(7, user.getBirthday());
            ps.setObject(8, user.getGender());
            ps.setObject(9, user.getUserGoal());
            ps.setObject(10, user.getLifestyle());
            ps.setObject(11, user.getRole());
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
    public Optional<User> findById(Integer id) {
        return findByParam(id, SELECT_BY_ID_QUERY);
    }

    @Override
    public boolean update(User user) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_QUERY)) {
            ps.setObject(1, user.getEmail());
            ps.setObject(2, hashpw(user.getPassword(), gensalt()));
            ps.setObject(3, user.getFirstName());
            ps.setObject(4, user.getLastName());
            ps.setObject(5, user.getHeight());
            ps.setObject(6, user.getWeight());
            ps.setObject(7, user.getBirthday());
            ps.setObject(8, user.getGender());
            ps.setObject(9, user.getUserGoal());
            ps.setObject(10, user.getLifestyle());
            ps.setObject(11, user.getRole());
            ps.setObject(12, user.getId());
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
