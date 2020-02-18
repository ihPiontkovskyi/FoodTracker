package ua.foodtracker.injector.loader;

import org.apache.log4j.Logger;
import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.annotation.Dao;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.command.Command;
import ua.foodtracker.dao.AnnotationHandler;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    private Map<String, Command> urlToCommand = new HashMap<>();

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
            manageCommands();
        } catch (ReflectiveOperationException e) {
            LOGGER.error("Cannot load bean(s). Cause", e);
        } catch (IOException e) {
            LOGGER.error("Cannot load resources. Cause", e);
        }
        LOGGER.info("All beans loaded successfully");
    }

    @Override
    protected void loadBean(Class<?> clazz) throws ReflectiveOperationException {
        if (clazz.isAnnotationPresent(Dao.class)) {
            loadDao(clazz);
            return;
        }
        if (clazz.isAnnotationPresent(Service.class)) {
            loadService(clazz);
            return;
        }
        if (clazz.isAnnotationPresent(ValidatorClass.class)) {
            loadValidator(clazz);
            return;
        }
        if (clazz.isAnnotationPresent(CommandMapping.class)) {
            loadCommandMapping(clazz);
        }
    }

    private void loadDao(Class<?> clazz) throws ReflectiveOperationException {
        Constructor<?> constructor = clazz.getConstructor(ConnectionHolder.class);
        Object o = constructor.newInstance(connectionHolder);
        String s = getNameByImplType(o, "Dao");
        if (s != null) {
            beans.put(s, o);
        } else {
            LOGGER.debug(String.format("Dao %s wasn't logged because its not dao", o));
        }
    }

    private void loadService(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
        String s = getNameByImplType(o, "Service");
        if (s != null) {
            services.put(s, o);
            return;
        }
        LOGGER.debug(String.format("Service %s wasn't logged Because its not repository", o));
    }

    private void loadValidator(Class<?> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
        String s = getNameByImplType(o, "Validator");
        if (s != null) {
            services.put(s, o);
            return;
        }
        LOGGER.debug(String.format("Validator %s wasn't logged Because its not repository", o));
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

    private void loadCommandMapping(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
        boolean flag = false;
        for (Object obj : o.getClass().getInterfaces()) {
            if (obj.toString().contains("Command")) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (String pattern : clazz.getDeclaredAnnotation(CommandMapping.class).urlPatterns()) {
                if (urlToCommand.containsKey(pattern)) {
                    throw new IllegalStateException("There is command with such url pattern!");
                }
                urlToCommand.put(pattern, (Command) o);
            }
        } else {
            LOGGER.debug(String.format("Url %s wasn't mapped because its not command", o));
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

    private void manageCommands() {
        servletContext.setAttribute("urlToCommandMap", urlToCommand);
    }

    @SuppressWarnings("squid:S1172")
    public void destroy(ServletContext context) {
        connectionManager.shutdown();
    }

    private String getNameByImplType(Object o, String dao) {
        String s = null;
        for (Object obj : o.getClass().getInterfaces()) {
            if (obj.toString().contains(dao)) {
                s = obj.toString().replace("interface ", "");
                break;
            }
        }
        return s;
    }
}