package ua.foodtracker.dao.db;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.dao.db.manager.HikariCPManager;

import static org.junit.Assert.assertNotNull;

public class HikariCPManagerTest {
    private static final String ACTUAL_DATABASE_PROPERTIES_FILENAME = "properties/db";
    private static final String TEST_DATABASE_PROPERTIES_FILENAME = "properties/h2_test_db";
    private static final String EXPECTED_MESSAGE = "Connection wasn't set ";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConfiguration() {
        HikariCPManager manager = new HikariCPManager(ACTUAL_DATABASE_PROPERTIES_FILENAME);
        assertNotNull(manager);
        assertNotNull(manager.getConnection());
        manager.shutdown();
    }

    @Test
    public void testH2Configuration() {
        HikariCPManager manager = new HikariCPManager(TEST_DATABASE_PROPERTIES_FILENAME);
        assertNotNull(manager);
        assertNotNull(manager.getConnection());
        manager.shutdown();
    }

    @Test
    public void testConnectionShouldThrowIllegalStateException() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage(EXPECTED_MESSAGE);
        HikariCPManager manager = new HikariCPManager(ACTUAL_DATABASE_PROPERTIES_FILENAME);
        manager.shutdown();
        manager.getConnection();
    }
}
