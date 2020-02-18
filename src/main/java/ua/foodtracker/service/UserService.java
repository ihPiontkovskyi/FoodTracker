package ua.foodtracker.service;

import ua.foodtracker.annotation.Transactional;
import ua.foodtracker.domain.User;

import java.util.Locale;
import java.util.Optional;

public interface UserService {
    User login(String email, String pass);

    @Transactional
    void register(User user);

    @Transactional
    void modify(User user);

}
