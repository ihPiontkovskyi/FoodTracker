package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.entity.Record;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.validator.impl.RecordValidator;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordValidator recordValidator;

    @Override
    public List<Record> getRecordsByDate(Integer userId, Date date) {
        return recordDao.findByUserIdAndDate(userId, date);
    }

    @Override
    public boolean add(Record record) {
        recordValidator.validate(record);
        if (!recordValidator.hasErrors()) {
            Integer id = recordDao.save(record);
            return id != null && id != 0;
        } else {
            throw new ValidationException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public boolean delete(Integer id) {
        return recordDao.deleteById(id);
    }

    @Override
    public boolean modify(Record record) {
        recordValidator.validate(record);
        if (!recordValidator.hasErrors()) {
            return recordDao.update(record);
        } else {
            throw new ValidationException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public Optional<Record> findById(Integer id) {
        return recordDao.findById(id);
    }

    @Override
    public void setLocale(Locale locale) {
        recordValidator.setLocale(locale);
    }
}
