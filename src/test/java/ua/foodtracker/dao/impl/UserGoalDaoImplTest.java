package ua.foodtracker.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.BaseDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.entity.UserGoal;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UserGoalDaoImplTest {
    private static final String SCHEMA_PATH = "src/test/resources/database/schema.sql";
    private static final String DATA_PATH = "src/test/resources/database/data.sql";
    private static final String DATABASE_PATH = "properties/h2_test_db";

    private HikariCPManager manager;
    private UserGoal userGoalForTest;
    private UserGoal containedUserGoal;
    private ConnectionHolder holder;
    private BaseDao<UserGoal> dao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        try {
            manager = new HikariCPManager(DATABASE_PATH);
            holder = new ThreadLocalConnectionHolder();
            Connection connection = manager.getConnection();
            final Statement executeStatement = connection.createStatement();
            String schemaQuery = new String(Files.readAllBytes(Paths.get(SCHEMA_PATH)));
            executeStatement.execute(schemaQuery);
            String dataQuery = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
            executeStatement.execute(dataQuery);
            executeStatement.close();
            connection.close();
            holder.set(manager.getConnection());
            dao = new UserGoalDaoImpl(holder);
            setContainedEntities();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void findByIdShouldReturnUserGoal() {
        Optional<UserGoal> userGoal = dao.findById(containedUserGoal.getId());
        assertTrue(userGoal.isPresent());
    }

    @Test
    public void saveShouldReturnInteger() {
        assertNotNull(dao.save(userGoalForTest));
    }

    @Test
    public void updateShouldReturnTrue() {
        assertTrue(dao.update(containedUserGoal));
    }

    @Test
    public void updateShouldReturnFalse() {
        assertFalse(dao.update(userGoalForTest));
    }

    @Test
    public void deleteByIdShouldThrowDataAccessExceptionBecauseOnDeleteRestrict() {
        exception.expect(DatabaseInteractionException.class);
        dao.deleteById(containedUserGoal.getId());
    }

    @Test
    public void deleteByIdShouldReturnFalse() {
        assertFalse(dao.deleteById(0));
    }

    @After
    public void close() throws SQLException {
        holder.get().close();
        holder.remove();
        manager.shutdown();
    }

    private void setContainedEntities() {
        containedUserGoal = UserGoal.builder()
                .withId(1)
                .withDailyEnergyGoal(2600)
                .withDailyCarbohydrateGoal(180)
                .withDailyFatGoal(40)
                .withDailyProteinGoal(55)
                .withDailyWaterGoal(2200)
                .build();
        userGoalForTest = UserGoal.builder()
                .withId(0)
                .withDailyEnergyGoal(2600)
                .withDailyCarbohydrateGoal(180)
                .withDailyFatGoal(40)
                .withDailyProteinGoal(55)
                .withDailyWaterGoal(2200)
                .build();
    }
}
