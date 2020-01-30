package ua.foodtracker.dao;

import java.util.List;
/**
 * Crud pageable dao class which contain methods interaction with database for all entities with pagination support
 */
public interface CrudPageableDao<E> extends CrudDao<E> {
    List<E> findAll(Page page);

    Long count();
}
