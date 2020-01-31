package ua.foodtracker.dao;

import java.util.List;

/**
 * Pageable dao class which contain methods interaction with database for all entities with pagination support
 */
public interface PageableDao<E> extends BaseDao<E> {
    List<E> findAll(Page page);

    Long count();
}
