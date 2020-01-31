package ua.foodtracker.dao.db.holder;

import java.sql.Connection;

/**
 * Used to hold a {@link Connection}
 */
public interface ConnectionHolder {

    /**
     * Used to get connection
     *
     * @return connection
     */
    Connection get();

    /**
     * Used to set a connection
     *
     * @param connection connection to be set
     */
    void set(Connection connection);

    /**
     * Removes a connection from holder
     */
    void remove();
}
