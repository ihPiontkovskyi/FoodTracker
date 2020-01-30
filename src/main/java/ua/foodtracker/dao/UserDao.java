package ua.foodtracker.dao;

import ua.foodtracker.entity.User;

import java.util.Optional;

/**
 * Record dao class which contain methods interaction users table
 */
public interface UserDao extends CrudPageableDao<User> {
    Optional<User> findByEmail(String email);
}
