package ua.foodtracker.service;

import ua.foodtracker.annotation.Transactional;
import ua.foodtracker.entity.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
    User login(String email, String pass);

    @Transactional
    boolean register(User user);

    @Transactional
    boolean modify(User user);

    Optional<User> findById(Integer id);

    boolean delete(Integer id);

    List<User> getPage(String pageNumber);

    long getPageCount();

    void setLocale(Locale locale);
}
