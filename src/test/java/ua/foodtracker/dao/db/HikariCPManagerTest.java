package ua.foodtracker.dao.db;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.db.manager.HikariCPManager;

import static org.junit.Assert.assertNotNull;

public class HikariCPManagerTest {
    private static final String FILENAME = "properties/db";
    private static final String EXPECTED_MESSAGE = "Connection wasn't set ";

    @Rule
    private ExpectedException exception = ExpectedException.none();

    @Test
    public void testConfiguration() {
        HikariCPManager manager = new HikariCPManager(FILENAME);
        assertNotNull(manager);
        assertNotNull(manager.getConnection());
        manager.shutdown();
    }

    @Test
    public void testConnectionShouldThrowIllegalStateException() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage(EXPECTED_MESSAGE);
        HikariCPManager manager = new HikariCPManager(FILENAME);
        manager.shutdown();
        manager.getConnection();
    }
}
