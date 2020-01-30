package ua.foodtracker.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.CrudPageableDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.User;
import ua.foodtracker.entity.UserGoal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MealDaoImplTest {
    private static final String SCHEMA_PATH = "src/test/resources/database/schema.sql";
    private static final String DATA_PATH = "src/test/resources/database/data.sql";
    private static final String DATABASE_PATH = "properties/h2_test_db";

    private Meal containedMeal;
    private HikariCPManager manager;
    private Meal mealForTest;
    private ConnectionHolder holder;
    private CrudPageableDao<Meal> dao;

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
            dao = new MealDaoImpl(holder);
            setContainedEntities();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(dao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnMeal() {
        Optional<Meal> meal = dao.findById(containedMeal.getId());
        assertTrue(meal.isPresent());
    }

    @Test
    public void countShouldReturnValueGraterThen0() {
        assertTrue(dao.count() > 0);
    }

    @Test
    public void saveShouldReturnInteger() {
        assertNotNull(dao.save(mealForTest));
    }

    @Test
    public void updateShouldReturnTrue() {
        assertTrue(dao.update(containedMeal));
    }

    @Test
    public void updateShouldReturnFalse() {
        assertFalse(dao.update(mealForTest));
    }

    @Test
    public void deleteByIdShouldReturnTrue() {
        assertTrue(dao.deleteById(containedMeal.getId()));
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
        containedMeal = Meal.builder()
                .withId(3)
                .withUser(containedUser)
                .withFat(2)
                .withCarbohydrates(6)
                .withProtein(1)
                .withWater(30)
                .withWeight(100)
                .withName("borshch")
                .build();
        mealForTest = Meal.builder()
                .withId(0)
                .withCarbohydrates(containedMeal.getCarbohydrates())
                .withFat(containedMeal.getFat())
                .withName(containedMeal.getName())
                .withProtein(containedMeal.getProtein())
                .withWater(containedMeal.getWater())
                .withWeight(containedMeal.getWeight())
                .withUser(containedUser)
                .build();
    }
}
