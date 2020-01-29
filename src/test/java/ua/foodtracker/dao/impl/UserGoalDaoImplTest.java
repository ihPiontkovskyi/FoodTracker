package ua.foodtracker.dao.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.CrudDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.entity.UserGoal;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UserGoalDaoImplTest {
    private static final String FILENAME = "properties/db";
    private static final HikariCPManager manager = new HikariCPManager(FILENAME);
    public static final UserGoal USER_GOAL = UserGoal.builder()
            .withId(0)
            .withDailyCarbohydrateGoal(140)
            .withDailyEnergyGoal(2200)
            .withDailyFatGoal(40)
            .withDailyProteinGoal(60)
            .withDailyWaterGoal(2000)
            .build();

    private ConnectionHolder holder = new ThreadLocalConnectionHolder();

    private CrudDao<UserGoal> dao;

    private Integer containedId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initHolderAndDao() {
        try {
            holder.set(manager.getConnection());
            holder.get().setAutoCommit(false);
            dao = new UserGoalDaoImpl(holder);
            containedId = dao.save(USER_GOAL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeConnectionInHolder() {
        try {
            holder.get().rollback();
            holder.get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllShouldReturnList() {
        assertTrue(dao.findAll().size() > 0);
    }

    @Test
    public void findByIdShouldReturnUserGoal() {
        Optional<UserGoal> userGoal
                = dao.findById(containedId);
        assertTrue(userGoal.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<UserGoal> userGoal = dao.findById(null);
        Assert.assertFalse(userGoal.isPresent());
    }

    @Test
    public void deleteShouldReturnTrue() {
        assertTrue(dao.deleteById(containedId));
    }

    @Test
    public void updateTestReturnTrue() {
        Optional<UserGoal> userGoal = dao.findById(containedId);
        if (userGoal.isPresent()) {
            UserGoal newUserGoal = UserGoal.builder()
                    .withId(userGoal.get().getId())
                    .withDailyCarbohydrateGoal(140)
                    .withDailyEnergyGoal(2200)
                    .withDailyFatGoal(40)
                    .withDailyProteinGoal(60)
                    .withDailyWaterGoal(2000)
                    .build();
            assertTrue(dao.update(newUserGoal));
        } else {
            fail();
        }
    }

    @Test
    public void saveShouldReturnId() {
        Integer id = dao.save(USER_GOAL);
        assertNotNull(id);
    }
}
