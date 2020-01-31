package ua.foodtracker.service;

import ua.foodtracker.entity.Record;

import java.sql.Date;
import java.util.List;

public interface RecordService {
    List<Record> getPage(Integer userId, Date date);

    boolean addRecord(Record record);

    boolean deleteRecord(Record record);

    boolean modifyRecord(Record record);
}
