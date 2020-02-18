package ua.foodtracker.dao.db.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * An implementation of {@link ConnectionManager}, based on HikariCP tool.
 */
public class HikariCPManager implements ConnectionManager {
    private static final Logger LOGGER = Logger.getLogger(HikariCPManager.class);

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASS = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_POOL_SIZE = "db.pool.size";
    private static final String DB_TIMEOUT = "db.timeout";
    private static final String ERROR_MESSAGE = "Connection wasn't set %s";

    private static HikariConfig config = new HikariConfig();
    private HikariDataSource dataSource;

    /**
     * Instantiates a new manager from resource file.
     *
     * @param filename path to the resource file
     */
    public HikariCPManager(String filename) {
        ResourceBundle resource = ResourceBundle.getBundle(filename);
        config.setDriverClassName(resource.getString(DB_DRIVER));
        config.setJdbcUrl(resource.getString(DB_URL));
        config.setUsername(resource.getString(DB_USERNAME));
        config.setPassword(resource.getString(DB_PASS));
        config.setMaximumPoolSize(getIntProperty(resource, DB_POOL_SIZE));
        config.setConnectionTimeout(getIntProperty(resource, DB_TIMEOUT));
        this.dataSource = new HikariDataSource(config);
    }

    private int getIntProperty(ResourceBundle resource, String dbPoolSize) {
        return Integer.parseInt(resource.getString(dbPoolSize));
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(ERROR_MESSAGE, e);
            throw new IllegalStateException(String.format(ERROR_MESSAGE, ""), e);
        }
    }

    @Override
    public void shutdown() {
        dataSource.close();
    }
}