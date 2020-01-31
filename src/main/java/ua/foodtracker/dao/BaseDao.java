package ua.foodtracker.dao;

import java.util.Optional;

/**
 * Base dao class which contain methods interaction with database for all entities
 */
public interface BaseDao<E> {
    Integer save(E entity);

    Optional<E> findById(Integer id);

    boolean update(E entity);

    boolean deleteById(Integer id);
}
