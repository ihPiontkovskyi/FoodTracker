package ua.foodtracker.dao;

import ua.foodtracker.entity.RecordEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface RecordDao extends BaseDao<RecordEntity> {
    List<RecordEntity> findByUserIdAndDate(int id, LocalDate date);
}
