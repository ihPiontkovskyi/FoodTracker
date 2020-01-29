package ua.foodtracker.dao.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.CrudPageableDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MealDaoImplTest {

    private static final String FILENAME = "properties/db";
    private static final HikariCPManager manager = new HikariCPManager(FILENAME);
    private static Meal meal;

    private ConnectionHolder holder = new ThreadLocalConnectionHolder();
    private ConnectionHolder userConnectionHolder = new ThreadLocalConnectionHolder();

    private CrudPageableDao<Meal> dao;

    private Integer containedId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initHolderAndDao() {
        try {
            holder.set(manager.getConnection());
            holder.get().setAutoCommit(false);
            dao = new MealDaoImpl(holder);
            userConnectionHolder.set(manager.getConnection());
            UserDao userDao = new UserDaoImpl(userConnectionHolder);
            List<User> users = userDao.findAll(new Page(1, 1));
            if (users.size() > 0) {
                User user = users.get(0);
                meal = Meal.builder()
                        .withCarbohydrates(60)
                        .withFat(4)
                        .withName("name")
                        .withId(0)
                        .withProtein(10)
                        .withWater(0)
                        .withWeight(300)
                        .withUserId(user.getId())
                        .build();
                containedId = dao.save(meal);
            } else {
                fail();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeConnectionInHolder() {
        try {
            holder.get().rollback();
            userConnectionHolder.get().rollback();
            holder.get().close();
            userConnectionHolder.get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(dao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findAllShouldReturnEmptyList() {
        assertEquals(Collections.EMPTY_LIST, dao.findAll());
    }

    @Test
    public void findByIdShouldReturnRole() {
        Optional<Meal> role = dao.findById(containedId);
        assertTrue(role.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<Meal> role = dao.findById(null);
        Assert.assertFalse(role.isPresent());
    }


    @Test
    public void deleteShouldReturnTrue() {
        assertTrue(dao.deleteById(containedId));
    }

    @Test
    public void updateTestReturnTrue() {
        Optional<Meal> meal = dao.findById(containedId);
        if (meal.isPresent()) {
            Meal newMeal = Meal.builder()
                    .withId(meal.get().getId())
                    .withUserId(meal.get().getUserId())
                    .withCarbohydrates(200)
                    .withFat(100)
                    .withName("name")
                    .withProtein(10)
                    .withWater(100)
                    .withWeight(200)
                    .build();
            assertTrue(dao.update(newMeal));
        } else {
            fail();
        }
    }

    @Test
    public void saveShouldReturnId() {
        Integer id = dao.save(meal);
        assertNotNull(id);
    }

    @Test
    public void countShouldReturnNotNull(){
        assertNotNull(dao.count());
    }
}
