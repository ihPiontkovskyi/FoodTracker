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
import ua.foodtracker.entity.Role;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RoleDaoImplTest {

    private static final String FILENAME = "properties/db";
    private static final HikariCPManager manager = new HikariCPManager(FILENAME);
    private static final Role ROLE = new Role(0, "role");

    private ConnectionHolder holder = new ThreadLocalConnectionHolder();

    private CrudDao<Role> dao;

    private Integer containedId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initHolderAndDao() {
        try {
            holder.set(manager.getConnection());
            holder.get().setAutoCommit(false);
            dao = new RoleDaoImpl(holder);
            containedId = dao.save(ROLE);
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
    public void findByIdShouldReturnRole() {
        Optional<Role> role
                = dao.findById(containedId);
        assertTrue(role.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<Role> role = dao.findById(null);
        Assert.assertFalse(role.isPresent());
    }


    @Test
    public void deleteShouldReturnTrue() {
        assertTrue(dao.deleteById(containedId));
    }

    @Test
    public void updateTestReturnTrue() {
        Optional<Role> role = dao.findById(containedId);
        if (role.isPresent()) {
            Role newRole = new Role(role.get().getId(), "another role");
            assertTrue(dao.update(newRole));
        } else {
            fail();
        }
    }

    @Test
    public void saveShouldReturnId() {
        Integer id = dao.save(ROLE);
        assertNotNull(id);
    }
}
