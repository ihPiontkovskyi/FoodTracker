package ua.foodtracker.dao;

import ua.foodtracker.dao.entity.Record;

import java.sql.Date;
import java.util.List;

public interface RecordDao extends BaseDao<Record> {
    List<Record> findByUserIdAndDate(int id, Date date);
}
