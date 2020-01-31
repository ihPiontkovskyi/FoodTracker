package ua.foodtracker.service;

import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.Record;

import java.util.List;

public interface MealService {
    List<Record> getPage(Page page);

    Integer getPageCount();

    boolean addRecord(Record record);

    boolean deleteRecord(Record record);

    boolean modifyRecord(Record record);
}
