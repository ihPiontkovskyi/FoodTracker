package ua.foodtracker.dao.db.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariCPManager implements ConnectionManager {

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_POOL_SIZE = "db.pool.size";
    private static final String DB_TIMEOUT = "db.timeout";

    private static HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    public HikariCPManager(String filename) {
        ResourceBundle resource = ResourceBundle.getBundle(filename);
        config.setDriverClassName(resource.getString(DB_DRIVER));
        config.setJdbcUrl(resource.getString(DB_URL));
        config.setUsername(resource.getString(DB_USERNAME));
        config.setPassword(resource.getString(DB_PASSWORD));
        config.setMaximumPoolSize(Integer.parseInt(resource.getString(DB_POOL_SIZE)));
        config.setConnectionTimeout(Integer.parseInt(resource.getString(DB_TIMEOUT)));
        this.ds = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("Connection wasn't set", e);
        }
    }

    @Override
    public void shutdown() {
        ds.close();
    }
}