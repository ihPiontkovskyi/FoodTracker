package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.entity.Record;
import ua.foodtracker.service.RecordService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordDao dao;

    @Override
    public List<Record> getPage(Integer userId, Date date) {
        return dao.findByUserIdAndDate(userId, date);
    }

    @Override
    public boolean add(Record record) {
        return dao.save(record) != 0;
    }

    @Override
    public boolean delete(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean modify(Record record) {
        return dao.update(record);
    }

    @Override
    public Optional<Record> findById(Integer id) {
        return dao.findById(id);
    }
}
