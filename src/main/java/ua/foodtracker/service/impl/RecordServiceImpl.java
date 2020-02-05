package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.domain.Record;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.RecordValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ua.foodtracker.service.utility.EntityMapper.mapRecordToEntityRecord;
import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;

@Service
public class RecordServiceImpl implements RecordService {

    private static final String INCORRECT_DATA = "incorrect.data";

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordValidator recordValidator;

    @Override
    public List<RecordEntity> getRecordsByDate(int userId, LocalDate date) {
        return recordDao.findByUserIdAndDate(userId, Date.valueOf(date));
    }

    @Override
    public void add(Record record) {
        recordValidator.validate(record);
        if (!recordValidator.hasErrors()) {
            Integer id = recordDao.save(mapRecordToEntityRecord(record));
            if (id == null || id == 0) {
                recordValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            recordValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
        try {
            if (!recordDao.deleteById(Integer.parseInt(id))) {
                recordValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
            }
        } catch (NumberFormatException ex) {
            recordValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public void modify(Record record) {
        recordValidator.validate(record);
        if (!recordValidator.hasErrors()) {
            if (!recordDao.update(mapRecordToEntityRecord(record))) {
                recordValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public Optional<Record> findById(String id) {
        if (id == null) {
            recordValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
        try {
            return recordDao.findById(Integer.parseInt(id)).map(EntityMapper::mapEntityRecordToRecord);
        } catch (NumberFormatException ex) {
            recordValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(recordValidator.getMessages(), recordValidator.getLocale()));
        }
    }

    @Override
    public void setLocale(Locale locale) {
        recordValidator.setLocale(locale);
    }
}
