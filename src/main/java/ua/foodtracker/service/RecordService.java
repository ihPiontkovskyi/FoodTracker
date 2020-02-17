package ua.foodtracker.service;

import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.HomeModel;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;

import java.util.List;
import java.util.Locale;

public interface RecordService {
    List<Record> getRecordsByDate(User user, String date);

    void add(Record record);

    void delete(String id);

    void modify(Record record);

    Record findById(String id);

    void setLocale(Locale locale);

    DailySums calculateDailySums(User user, String date);

    HomeModel getHomeModel(User user);
}
