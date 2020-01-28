package ua.foodtracker.dao;

import ua.foodtracker.entity.Record;

import java.sql.Date;
import java.util.List;

public interface RecordDao extends CrudDao<Record> {
    List<Record> findByUserIdAndDate(int id, Date date);
}