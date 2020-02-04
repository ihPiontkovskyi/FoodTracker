package ua.foodtracker.service;

import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RecordService {
    List<Record> getRecordsByDate(Integer userId, Date date);

    boolean add(Record record);

    boolean delete(Integer id);

    boolean modify(Record record);

    Optional<Record> findById(Integer id);

    void setLocale(Locale locale);
}
