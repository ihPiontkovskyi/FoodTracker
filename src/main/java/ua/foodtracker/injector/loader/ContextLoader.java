package ua.foodtracker.injector.loader;

import org.apache.log4j.Logger;
import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Dao;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.AnnotationHandler;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ContextLoader extends AbstractContextLoader {
    private static final Logger LOGGER = Logger.getLogger(ContextLoader.class);
    private ServletContext servletContext;
    private ConnectionHolder connectionHolder;
    private HikariCPManager connectionManager;
    private Map<String, Object> beans = new HashMap<>();
    private Map<String, Object> services = new HashMap<>();

    public ContextLoader(ConnectionHolder holder, HikariCPManager manager, ServletContext context) {
        this.servletContext = context;
        this.connectionHolder = holder;
        this.connectionManager = manager;
    }

    public void load(String... packagesNames) {
        try {
            loadBeans(packagesNames);
            beans.putAll(services);
            autowireBeans();
            manageServices();
        } catch (ReflectiveOperationException e) {
            LOGGER.error("Cannot load bean(s). Cause", e);
        } catch (IOException e) {
            LOGGER.error("Cannot load resources. Cause", e);
        }
        LOGGER.info("All beans loaded successfully");
    }

    @Override
    protected void loadBean(Class<?> c) throws ReflectiveOperationException {
        if (c.isAnnotationPresent(Dao.class)) {
            loadDao(c);
            return;
        }
        if (c.isAnnotationPresent(Service.class)) {
            loadService(c);
        }
    }

    private void loadDao(Class<?> c) throws ReflectiveOperationException {
        Constructor<?> constructor = c.getConstructor(ConnectionHolder.class);
        Object o = constructor.newInstance(connectionHolder);
        String s = null;
        for (Object obj : o.getClass().getInterfaces()) {
            if (obj.toString().contains("Dao")) {
                s = obj.toString().replace("interface ", "");
                break;
            }
        }
        if (s != null) {
            beans.put(s, o);
        } else {
            LOGGER.debug(String.format("Dao %s wasn't logged because its not repository", o));
        }
    }

    private void loadService(Class<?> c) throws IllegalAccessException, InstantiationException {
        Object o = c.newInstance();
        String s = null;
        for (Object obj : o.getClass().getInterfaces()) {
            if (obj.toString().contains("Service")) {
                s = obj.toString().replace("interface ", "");
                break;
            }
        }
        if (s != null) {
            services.put(s, o);
            return;
        }
        LOGGER.debug(String.format("Service %s wasn't logged Because its not repository", o));
    }

    private void autowireBeans() throws IllegalAccessException {
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object existingBean = beans.get(field.getType().getName());
                    if (existingBean != null) {
                        field.set(bean, existingBean);
                    }
                }
            }
        }
    }

    private void manageServices() {
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            Object service = entry.getValue();
            Object proxy = Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    service.getClass().getInterfaces(),
                    new AnnotationHandler(connectionHolder, service, connectionManager)
            );
            servletContext.setAttribute(entry.getKey(), proxy);
        }
    }

    public void destroy(ServletContext context) {
        connectionManager.shutdown();
    }
}