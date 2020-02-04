package ua.foodtracker.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.dao.entity.Gender;
import ua.foodtracker.dao.entity.Lifestyle;
import ua.foodtracker.dao.entity.Meal;
import ua.foodtracker.dao.entity.Record;
import ua.foodtracker.dao.entity.Role;
import ua.foodtracker.dao.entity.User;
import ua.foodtracker.dao.entity.UserGoal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RecordDaoImplTest {
    private static final String SCHEMA_PATH = "src/test/resources/database/schema.sql";
    private static final String DATA_PATH = "src/test/resources/database/data.sql";
    private static final String DATABASE_PATH = "properties/h2_test_db";

    private HikariCPManager manager;
    private Record recordForTest;
    private Record containedRecord;
    private ConnectionHolder holder;
    private RecordDao dao;

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
            dao = new RecordDaoImpl(holder);
            setContainedEntities();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void findByIdShouldReturnRecord() {
        Optional<Record> record = dao.findById(containedRecord.getId());
        assertTrue(record.isPresent());
    }

    @Test
    public void saveShouldReturnInteger() {
        assertNotNull(dao.save(recordForTest));
    }

    @Test
    public void updateShouldReturnTrue() {
        assertTrue(dao.update(containedRecord));
    }

    @Test
    public void updateShouldReturnFalse() {
        assertFalse(dao.update(recordForTest));
    }

    @Test
    public void deleteByIdShouldReturnFalse() {
        assertFalse(dao.deleteById(0));
    }

    @Test
    public void findByUserAndDateShouldReturnEmptyList() {
        assertEquals(0, dao.findByUserIdAndDate(0, Date.valueOf("2020-01-27")).size());
    }

    @Test
    public void findByUserAndDateShouldReturnList() {
        assertTrue(dao.findByUserIdAndDate(containedRecord.getMeal().getUser().getId(), containedRecord.getDate()).size() > 0);
    }

    @After
    public void close() throws SQLException {
        holder.get().close();
        holder.remove();
        manager.shutdown();
    }

    private void setContainedEntities() {
        UserGoal containedUserGoal = UserGoal.builder()
                .withId(1)
                .withDailyEnergyGoal(2600)
                .withDailyCarbohydrateGoal(180)
                .withDailyFatGoal(40)
                .withDailyProteinGoal(55)
                .withDailyWaterGoal(2200)
                .build();
        User containedUser = User.builder()
                .withId(2)
                .withEmail("user@mail")
                .withPassword("$2y$12$.obmNC3vIgT1XGBbfJnHeeD1A5aRw/JiTi.hzmAVuZbcj8X3dGr/6")
                .withFirstName("Ivan")
                .withLastName("Ivanov")
                .withHeight(190)
                .withWeight(80)
                .withUserGoal(containedUserGoal)
                .withGender(Gender.MALE)
                .withRole(Role.USER)
                .withLifestyle(Lifestyle.SEDENTARY)
                .withBirthday(Date.valueOf("1994-01-29"))
                .build();
        Meal containedMeal = Meal.builder()
                .withId(3)
                .withUser(containedUser)
                .withFat(2)
                .withCarbohydrates(6)
                .withProtein(1)
                .withWater(30)
                .withWeight(100)
                .withName("borshch")
                .build();
        containedRecord = Record.builder()
                .withId(3)
                .withMeal(containedMeal)
                .withDate(Date.valueOf("2020-01-27"))
                .withUserId(2)
                .build();
        recordForTest = Record.builder()
                .withId(0)
                .withMeal(containedMeal)
                .withUserId(2)
                .withDate(Date.valueOf("2020-01-27"))
                .build();
    }
}
