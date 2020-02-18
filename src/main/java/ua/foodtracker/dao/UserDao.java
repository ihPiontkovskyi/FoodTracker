package ua.foodtracker.dao;

import ua.foodtracker.entity.UserEntity;

import java.util.Optional;

/**
 * User dao class which contain methods interaction with users table in DB.
 */
public interface UserDao extends BaseDao<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}
