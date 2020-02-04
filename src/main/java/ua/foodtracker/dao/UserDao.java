package ua.foodtracker.dao;

import ua.foodtracker.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    List<User> findAll(Page page);

    long count();

    Optional<User> findByEmail(String email);
}
