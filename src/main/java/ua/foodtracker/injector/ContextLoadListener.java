package ua.foodtracker.injector;

import ua.foodtracker.dao.db.holder.ThreadLocalConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.injector.loader.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class ContextLoadListener implements ServletContextListener {

    private ContextLoader loader;
    private static final String DB_FILENAME = "properties/db";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loader = new ContextLoader(
                new ThreadLocalConnectionHolder(), new HikariCPManager(DB_FILENAME), sce.getServletContext());
        loader.load("ua.foodtracker.dao", "ua.foodtracker.service");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        loader.destroy(sce.getServletContext());
    }

}
