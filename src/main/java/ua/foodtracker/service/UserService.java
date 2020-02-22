package ua.foodtracker.service;

import ua.foodtracker.annotation.Transactional;
import ua.foodtracker.domain.User;

public interface UserService {
    User login(String email, String pass);

    @Transactional
    void register(User user);

    @Transactional
    void modify(User user);

}
