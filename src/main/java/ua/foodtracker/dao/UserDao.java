package ua.foodtracker.dao;

import ua.foodtracker.entity.User;

import java.util.Optional;

public interface UserDao extends CrudPageableDao<User> {
    Optional<User> findByEmail(String email);
}
