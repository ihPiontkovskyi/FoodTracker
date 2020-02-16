package ua.foodtracker.dao;

import ua.foodtracker.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends BaseDao<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}
