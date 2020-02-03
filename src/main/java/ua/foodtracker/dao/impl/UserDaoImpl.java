package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.User;
import ua.foodtracker.entity.UserGoal;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static ua.foodtracker.utility.EntityMapper.extractUserFromResultSet;
import static ua.foodtracker.utility.Query.getQuery;

@Dao
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    private static final String FIND_BY_EMAIL_QUERY_KEY = "users.find.by.email";
    private static final String FIND_PAGE_QUERY_KEY = "users.find.page";
    private static final String COUNT_RECORD_QUERY_KEY = "users.get.count";
    private static final String FIND_BY_ID_QUERY_KEY = "users.find.by.id";
    private static final String DELETE_QUERY_KEY = "users.delete.by.id";
    private static final String INSERT_QUERY_KEY = "users.insert";
    private static final String UPDATE_QUERY_KEY = "users.update";

    private static final String INSERT_GOAL_QUERY = "users.goals.insert";
    private static final String UPDATE_GOAL_QUERY = "users.goals.update";

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
        String query = getQuery(INSERT_GOAL_QUERY);
        try (PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareData(user.getUserGoal(), ps);
            ps.execute();
            int id = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            if (id != 0) {
                UserGoal goal = UserGoal.builder().
                        withId(id)
                        .withDailyCarbohydrateGoal(user.getUserGoal().getDailyCarbohydrateGoal())
                        .withDailyEnergyGoal(user.getUserGoal().getDailyEnergyGoal())
                        .withDailyFatGoal(user.getUserGoal().getDailyFatGoal())
                        .withDailyProteinGoal(user.getUserGoal().getDailyProteinGoal())
                        .withDailyWaterGoal(user.getUserGoal().getDailyWaterGoal())
                        .build();
                User userForSave = User.builder()
                        .withId(user.getId())
                        .withUserGoal(goal)
                        .withBirthday(user.getBirthday())
                        .withEmail(user.getEmail())
                        .withFirstName(user.getFirstName())
                        .withGender(user.getGender())
                        .withHeight(user.getHeight())
                        .withLastName(user.getLastName())
                        .withLifestyle(user.getLifestyle())
                        .withPassword((user.getPassword()))
                        .withRole(user.getRole())
                        .withWeight(user.getWeight())
                        .build();
                return save(userForSave, INSERT_QUERY_KEY);
            } else {
                throw new DatabaseInteractionException(getMessage(INSERT_GOAL_QUERY));
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY_KEY);
    }

    @Override
    public boolean update(User user) {
        String query = getQuery(UPDATE_GOAL_QUERY);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            prepareDataWithId(user.getUserGoal(), ps);
            ps.execute();
            return update(user, UPDATE_QUERY_KEY);
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, query, e));
            throw new DatabaseInteractionException(getMessage(query), e);
        }
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
    protected void prepareDataWithId(User user, PreparedStatement ps) throws SQLException {
        prepareData(user, ps);
        ps.setObject(12, user.getId());
    }

    private void prepareData(UserGoal userGoal, PreparedStatement ps) throws SQLException {
        ps.setObject(1, userGoal.getDailyEnergyGoal());
        ps.setObject(2, userGoal.getDailyFatGoal());
        ps.setObject(3, userGoal.getDailyProteinGoal());
        ps.setObject(4, userGoal.getDailyWaterGoal());
        ps.setObject(5, userGoal.getDailyCarbohydrateGoal());
    }

    private void prepareDataWithId(UserGoal userGoal, PreparedStatement ps) throws SQLException {
        prepareData(userGoal, ps);
        ps.setObject(6, userGoal.getId());
    }
}
