package ua.foodtracker.dao.impl;

import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static ua.foodtracker.utility.EntityMapper.extractUserFromResultSet;

public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    private static final String FIND_BY_EMAIL_QUERY_KEY = "users.find.by.email";
    private static final String FIND_PAGE_QUERY_KEY = "users.find.page";
    private static final String COUNT_RECORD_QUERY_KEY = "users.get.count";
    private static final String FIND_BY_ID_QUERY_KEY = "users.find.by.id";
    private static final String DELETE_QUERY_KEY = "users.delete.by.id";
    private static final String INSERT_QUERY_KEY = "users.insert";
    private static final String UPDATE_QUERY_KEY = "users.update";

    public UserDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractUserFromResultSet(resultSet);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY_KEY);
    }

    @Override
    public List<User> findAll(Page page) {
        return findAll(FIND_PAGE_QUERY_KEY, page);
    }

    @Override
    public Long count() {
        return count(COUNT_RECORD_QUERY_KEY);
    }

    @Override
    public Integer save(User user) {
        return save(user, INSERT_QUERY_KEY);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY_KEY);
    }

    @Override
    public boolean update(User user) {
        return update(user, UPDATE_QUERY_KEY);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY_KEY);
    }

    @Override
    protected void prepareData(User user, PreparedStatement ps) throws SQLException {
        ps.setObject(1, user.getEmail());
        ps.setObject(2, hashpw(user.getPassword(), gensalt()));
        ps.setObject(3, user.getFirstName());
        ps.setObject(4, user.getLastName());
        ps.setObject(5, user.getHeight());
        ps.setObject(6, user.getWeight());
        ps.setObject(7, user.getBirthday());
        ps.setObject(8, user.getGender().getId());
        ps.setObject(9, user.getUserGoal().getId());
        ps.setObject(10, user.getLifestyle().getId());
        ps.setObject(11, user.getRole().getId());
    }

    @Override
    protected void prepareWithId(User user, PreparedStatement ps) throws SQLException {
        prepareData(user, ps);
        ps.setObject(12, user.getId());
    }
}
