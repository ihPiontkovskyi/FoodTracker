package ua.foodtracker.dao;

import ua.foodtracker.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Record dao class which contain methods interaction users table
 */
public interface UserDao extends BaseDao<User> {
    List<User> findAll(Page page);

    Long count();

    Optional<User> findByEmail(String email);
}
