package ua.foodtracker.dao.db.holder;

import java.sql.Connection;

/**
 * This is a thread-safe implementation of {@link ConnectionHolder}
 * interface.
 *
 * @see ThreadLocal
 */
public class ThreadLocalConnectionHolder implements ConnectionHolder {
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public Connection get() {
        return connectionHolder.get();
    }

    @Override
    public void set(Connection connection) {
        connectionHolder.set(connection);
    }

    @Override
    public void remove() {
        connectionHolder.remove();
    }
}
