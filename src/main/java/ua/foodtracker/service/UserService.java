package ua.foodtracker.service;

import ua.foodtracker.annotation.Transactional;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.service.domain.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
    User login(String email, String pass);

    @Transactional
    void register(User user);

    @Transactional
    void modify(User user);

    Optional<User> findById(String id);

    void delete(String id);

    List<User> getPage(Integer pageNumber);

    long getPageCount();

    void setLocale(Locale locale);
}
