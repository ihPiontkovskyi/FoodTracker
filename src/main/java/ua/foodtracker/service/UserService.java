package ua.foodtracker.service;

import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.User;

import java.util.List;

public interface UserService {
    boolean login(String email, String pass);

    boolean register(User user);

    boolean modify(User user);

    boolean delete(User user);

    List<User> getPage(Page page);

    Integer getPageCount();
}
