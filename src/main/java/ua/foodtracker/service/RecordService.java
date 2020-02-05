package ua.foodtracker.service;

import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.service.domain.Record;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RecordService {
    List<RecordEntity> getRecordsByDate(int userId, LocalDate date);

    void add(Record record);

    void delete(String id);

    void modify(Record record);

    Optional<Record> findById(String id);

    void setLocale(Locale locale);
}
