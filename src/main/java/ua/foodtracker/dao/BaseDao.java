package ua.foodtracker.dao;

import java.util.Optional;

public interface BaseDao<E> {
    Integer save(E entity);

    Optional<E> findById(Integer id);

    boolean update(E entity);

    boolean deleteById(Integer id);
}
