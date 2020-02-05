package ua.foodtracker.service;

import ua.foodtracker.entity.Record;
import ua.foodtracker.service.entity.RawRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RecordService {
    List<Record> getRecordsByDate(int userId, LocalDate date);

    void add(RawRecord record);

    void delete(String id);

    void modify(RawRecord record);

    Optional<Record> findById(Integer id);

    void setLocale(Locale locale);
}
