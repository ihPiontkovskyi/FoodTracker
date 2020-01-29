package ua.foodtracker.dao;

import java.util.Collections;
import java.util.List;

public interface CrudPageableDao<E> extends CrudDao<E> {
    default List<E> findAll(Page page) {
        return Collections.emptyList();
    }

    Long count();
}
