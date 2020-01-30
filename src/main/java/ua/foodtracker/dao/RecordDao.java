package ua.foodtracker.dao;

import ua.foodtracker.entity.Record;

import java.sql.Date;
import java.util.List;

/**
 * Record dao class which contain methods interaction records table
 */
public interface RecordDao extends CrudDao<Record> {
    List<Record> findByUserIdAndDate(int id, Date date);
}
