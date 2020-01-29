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
import ua.foodtracker.entity.Gender;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GenderDaoImplTest {
    private static final String FILENAME = "properties/db";
    private static final HikariCPManager manager = new HikariCPManager(FILENAME);
    public static final Gender GENDER = new Gender(0, "male");

    private ConnectionHolder holder = new ThreadLocalConnectionHolder();

    private CrudDao<Gender> dao;

    private Integer containedId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initHolderAndDao() {
        try {
            holder.set(manager.getConnection());
            holder.get().setAutoCommit(false);
            dao = new GenderDaoImpl(holder);
            containedId = dao.save(GENDER);
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
    public void findByIdShouldReturnGender() {
        Optional<Gender> gender = dao.findById(containedId);
        assertTrue(gender.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<Gender> gender = dao.findById(null);
        Assert.assertFalse(gender.isPresent());
    }

    @Test
    public void deleteShouldReturnTrue() {
        assertTrue(dao.deleteById(containedId));
    }

    @Test
    public void updateTestReturnTrue() {
        Optional<Gender> gender = dao.findById(containedId);
        if (gender.isPresent()) {
            Gender newGender = new Gender(gender.get().getId(), "gender");
            assertTrue(dao.update(newGender));
        } else {
            fail();
        }
    }

    @Test
    public void saveShouldReturnId() {
        Integer id = dao.save(new Gender(0, "gender"));
        assertNotNull(id);
    }
}
