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
import ua.foodtracker.entity.Lifestyle;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LifestyleDaoImplTest {
    private static final String FILENAME = "properties/db";
    private static final HikariCPManager manager = new HikariCPManager(FILENAME);
    public static final Lifestyle LIFESTYLE = Lifestyle.builder()
            .withId(0)
            .withName("lifestyle")
            .withDescription("description")
            .withEnergyCoefficient(5.0).build();

    private ConnectionHolder holder = new ThreadLocalConnectionHolder();

    private CrudDao<Lifestyle> dao;

    private Integer containedId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initHolderAndDao() {
        try {
            holder.set(manager.getConnection());
            holder.get().setAutoCommit(false);
            dao = new LifestyleDaoImpl(holder);
            containedId = dao.save(LIFESTYLE);
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
    public void findByIdShouldReturnLifestyle() {
        Optional<Lifestyle> lifestyle = dao.findById(containedId);
        assertTrue(lifestyle.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<Lifestyle> lifestyle = dao.findById(null);
        Assert.assertFalse(lifestyle.isPresent());
    }

    @Test
    public void deleteShouldReturnTrue() {
        assertTrue(dao.deleteById(containedId));
    }

    @Test
    public void updateTestReturnTrue() {
        Optional<Lifestyle> lifestyle = dao.findById(containedId);
        if (lifestyle.isPresent()) {
            Lifestyle newLifestyle =
                    Lifestyle.builder()
                            .withId(lifestyle.get().getId())
                            .withName("another lifestyle")
                            .withDescription("some more description")
                            .withEnergyCoefficient(4.0)
                            .build();
            assertTrue(dao.update(newLifestyle));
        } else {
            fail();
        }
    }

    @Test
    public void saveShouldReturnId() {
        Integer id = dao.save(
                Lifestyle.builder()
                        .withId(0)
                        .withName("another lifestyle")
                        .withDescription("some more description")
                        .withEnergyCoefficient(4.0)
                        .build());
        assertNotNull(id);
    }
}
