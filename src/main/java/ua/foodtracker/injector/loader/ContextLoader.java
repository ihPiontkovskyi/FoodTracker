package ua.foodtracker.injector.loader;

import org.apache.log4j.Logger;
import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Dao;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.ErrorCommand;
import ua.foodtracker.command.impl.HomePageCommand;
import ua.foodtracker.command.impl.LoginCommand;
import ua.foodtracker.command.impl.LoginPageCommand;
import ua.foodtracker.command.impl.LogoutCommand;
import ua.foodtracker.command.impl.RegisterCommand;
import ua.foodtracker.command.impl.RegisterPageCommand;
import ua.foodtracker.command.impl.meal.AddMealCommand;
import ua.foodtracker.command.impl.meal.AddMealPageCommand;
import ua.foodtracker.command.impl.meal.EditMealCommand;
import ua.foodtracker.command.impl.meal.EditMealPageCommand;
import ua.foodtracker.command.impl.meal.MealDeleteCommand;
import ua.foodtracker.command.impl.meal.MealPageCommand;
import ua.foodtracker.command.impl.meal.MealsInfoCommand;
import ua.foodtracker.command.impl.record.AddRecordCommand;
import ua.foodtracker.command.impl.record.DiaryPageCommand;
import ua.foodtracker.command.impl.record.RecordDeleteCommand;
import ua.foodtracker.command.impl.user.ModifyProfileCommand;
import ua.foodtracker.command.impl.user.ProfilePageCommand;
import ua.foodtracker.dao.AnnotationHandler;
import ua.foodtracker.dao.db.holder.ConnectionHolder;
import ua.foodtracker.dao.db.manager.HikariCPManager;
import ua.foodtracker.validator.impl.AbstractValidator;

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
            loadCommands();
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
        }
    }

    private void loadDao(Class<?> clazz) throws ReflectiveOperationException {
        Constructor<?> constructor = clazz.getConstructor(ConnectionHolder.class);
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

    private void loadService(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
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

    private void loadValidator(Class<?> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
        if (o.getClass().getSuperclass() == AbstractValidator.class) {
            services.put(o.getClass().getName(), o);
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

    private void loadCommands() {
        Map<String, Command> urlToCommand = new HashMap<>();
        urlToCommand.put("/foodtracker.ua/user/logout", new LogoutCommand());
        urlToCommand.put("/foodtracker.ua/login", new LoginCommand());
        urlToCommand.put("/foodtracker.ua/user/home", new HomePageCommand());
        urlToCommand.put("/foodtracker.ua/register", new RegisterCommand());
        urlToCommand.put("/foodtracker.ua/error", new ErrorCommand());
        urlToCommand.put("/foodtracker.ua/user/records", new DiaryPageCommand());
        urlToCommand.put("/foodtracker.ua/user/meals", new MealPageCommand());
        urlToCommand.put("/foodtracker.ua/user/records/delete", new RecordDeleteCommand());
        urlToCommand.put("/foodtracker.ua/user/meals/delete", new MealDeleteCommand());
        urlToCommand.put("/foodtracker.ua/user/records/byTerm", new MealsInfoCommand());
        urlToCommand.put("/foodtracker.ua/user/records/add", new AddRecordCommand());
        urlToCommand.put("/foodtracker.ua/user/meals/add-meal", new AddMealCommand());
        urlToCommand.put("/foodtracker.ua/user/meals/edit-meal", new EditMealCommand());
        urlToCommand.put("/foodtracker.ua/user/meal-add", new AddMealPageCommand());
        urlToCommand.put("/foodtracker.ua/user/meal-edit", new EditMealPageCommand());
        urlToCommand.put("/foodtracker.ua/login-page", new LoginPageCommand());
        urlToCommand.put("/foodtracker.ua/register-page", new RegisterPageCommand());
        urlToCommand.put("/foodtracker.ua/user/profile", new ProfilePageCommand());
        urlToCommand.put("/foodtracker.ua/user/profile-modify", new ModifyProfileCommand());
        servletContext.setAttribute("urlToCommandMap", urlToCommand);
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

    @SuppressWarnings("squid:S1172")
    public void destroy(ServletContext context) {
        connectionManager.shutdown();
    }
}