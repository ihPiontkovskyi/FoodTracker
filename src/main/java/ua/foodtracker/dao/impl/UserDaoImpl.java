package ua.foodtracker.dao.impl;

import ua.foodtracker.annotation.Dao;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;
import static ua.foodtracker.dao.utility.ResultSetToEntityMapper.extractUserFromResultSet;

@Dao
@SuppressWarnings("squid:S2068")
public class UserDaoImpl extends AbstractDaoImpl<UserEntity> implements UserDao {
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE email=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users LEFT JOIN user_goals ON users.user_goal_id=user_goals.id WHERE users.id=?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET email=?,password=?,first_name=?,last_name=?,height=?,weight=?,birthday=?,gender=?,user_goal_id=?,lifestyle=?,role=? WHERE id=?";

    private static final String INSERT_GOAL_QUERY = "INSERT INTO user_goals VALUES(DEFAULT,?,?,?,?,?)";
    private static final String UPDATE_GOAL_QUERY = "UPDATE user_goals SET daily_energy=?,daily_fat=?,daily_protein=?,daily_water=?,daily_carbohydrate=? WHERE id=?";

    public UserDaoImpl(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    protected UserEntity extractFromResultSet(ResultSet resultSet) throws SQLException {
        return extractUserFromResultSet(resultSet);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY);
    }

    @Override
    public Integer save(UserEntity userEntity) {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_GOAL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            prepareData(userEntity.getUserGoalEntity(), ps);
            ps.execute();
            int id = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            if (id != 0) {
                UserGoalEntity goal = UserGoalEntity.builder().
                        withId(id)
                        .withDailyCarbohydrateGoal(userEntity.getUserGoalEntity().getDailyCarbohydrateGoal())
                        .withDailyEnergyGoal(userEntity.getUserGoalEntity().getDailyEnergyGoal())
                        .withDailyFatGoal(userEntity.getUserGoalEntity().getDailyFatGoal())
                        .withDailyProteinGoal(userEntity.getUserGoalEntity().getDailyProteinGoal())
                        .withDailyWaterGoal(userEntity.getUserGoalEntity().getDailyWaterGoal())
                        .build();
                UserEntity userEntityForSave = UserEntity.builder()
                        .withId(userEntity.getId())
                        .withUserGoal(goal)
                        .withBirthday(userEntity.getBirthday())
                        .withEmail(userEntity.getEmail())
                        .withFirstName(userEntity.getFirstName())
                        .withGender(userEntity.getGenderEntity())
                        .withHeight(userEntity.getHeight())
                        .withLastName(userEntity.getLastName())
                        .withLifestyle(userEntity.getLifestyleEntity())
                        .withPassword((userEntity.getPassword()))
                        .withRole(userEntity.getRoleEntity())
                        .withWeight(userEntity.getWeight())
                        .build();
                return save(userEntityForSave, INSERT_QUERY);
            } else {
                throw new DatabaseInteractionException(getMessage(INSERT_GOAL_QUERY));
            }
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, INSERT_GOAL_QUERY, e));
            throw new DatabaseInteractionException(getMessage(INSERT_GOAL_QUERY), e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return findByParam(id, FIND_BY_ID_QUERY);
    }

    @Override
    public boolean update(UserEntity userEntity) {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_GOAL_QUERY)) {
            prepareDataWithId(userEntity.getUserGoalEntity(), ps);
            ps.execute();
            return update(userEntity, UPDATE_QUERY);
        } catch (SQLException e) {
            LOGGER.warn(String.format(ERROR_MESSAGE, UPDATE_GOAL_QUERY, e));
            throw new DatabaseInteractionException(getMessage(UPDATE_GOAL_QUERY), e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(id, DELETE_QUERY);
    }

    @Override
    protected void prepareData(UserEntity userEntity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, userEntity.getEmail());
        ps.setObject(2, hashpw(userEntity.getPassword(), gensalt()));
        ps.setObject(3, userEntity.getFirstName());
        ps.setObject(4, userEntity.getLastName());
        ps.setObject(5, userEntity.getHeight());
        ps.setObject(6, userEntity.getWeight());
        ps.setObject(7, userEntity.getBirthday());
        ps.setObject(8, userEntity.getGenderEntity().getId());
        ps.setObject(9, userEntity.getUserGoalEntity().getId());
        ps.setObject(10, userEntity.getLifestyleEntity().getId());
        ps.setObject(11, userEntity.getRoleEntity().getId());
    }

    @Override
    protected void prepareDataWithId(UserEntity userEntity, PreparedStatement ps) throws SQLException {
        prepareData(userEntity, ps);
        ps.setObject(12, userEntity.getId());
    }

    private void prepareData(UserGoalEntity userGoalEntity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, userGoalEntity.getDailyEnergyGoal());
        ps.setObject(2, userGoalEntity.getDailyFatGoal());
        ps.setObject(3, userGoalEntity.getDailyProteinGoal());
        ps.setObject(4, userGoalEntity.getDailyWaterGoal());
        ps.setObject(5, userGoalEntity.getDailyCarbohydrateGoal());
    }

    private void prepareDataWithId(UserGoalEntity userGoalEntity, PreparedStatement ps) throws SQLException {
        prepareData(userGoalEntity, ps);
        ps.setObject(6, userGoalEntity.getId());
    }
}
