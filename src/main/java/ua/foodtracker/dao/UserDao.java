package ua.foodtracker.dao;

import ua.foodtracker.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<UserEntity> {
    List<UserEntity> findAll(Page page);

    long count();

    Optional<UserEntity> findByEmail(String email);
}
