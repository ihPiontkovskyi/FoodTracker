package ua.foodtracker.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CrudDao<E> {
    Integer save(E entity);

    Optional<E> findById(Integer id);

    default List<E> findAll() {
        return Collections.emptyList();
    }

    boolean update(E entity);

    boolean deleteById(Integer id);
}
