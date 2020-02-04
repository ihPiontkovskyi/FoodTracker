package ua.foodtracker.service;

import ua.foodtracker.dao.entity.Record;
import ua.foodtracker.raw.type.entity.RawRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RecordService {
    List<Record> getRecordsByDate(Integer userId, LocalDate date);

    void add(RawRecord record);

    void delete(Integer id);

    void modify(RawRecord record);

    Optional<Record> findById(Integer id);

    void setLocale(Locale locale);
}
