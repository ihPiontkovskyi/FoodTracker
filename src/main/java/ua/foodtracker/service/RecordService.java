package ua.foodtracker.service;

import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.HomeModel;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;

import java.util.List;

public interface RecordService {
    List<Record> getRecordsByDate(User user, String date);

    void add(Record record);

    void delete(String id);

    Record findById(String id);

    DailySums calculateDailySums(User user, String date);

    HomeModel getHomeModel(User user);
}
