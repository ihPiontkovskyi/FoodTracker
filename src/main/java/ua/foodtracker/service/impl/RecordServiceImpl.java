package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.HomeModel;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.utility.DateProvider;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.RecordValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.foodtracker.service.utility.EntityMapper.mapRecordToEntityRecord;
import static ua.foodtracker.service.utility.ServiceUtility.addByType;
import static ua.foodtracker.service.utility.ServiceUtility.deleteByStringId;
import static ua.foodtracker.service.utility.ServiceUtility.findByStringParam;

@Service
public class RecordServiceImpl implements RecordService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM");

    private final DateProvider dateProvider = new DateProvider();

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordValidator recordValidator;

    @Override
    public List<Record> getRecordsByDate(User user, String date) {
        return recordDao.findByUserIdAndDate(user.getId(), dateProvider.parseOrCurrentDate(date)).stream()
                .map(EntityMapper::mapEntityRecordToRecord)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Record record) {
        addByType(record, recordValidator, obj -> recordDao.save(mapRecordToEntityRecord(obj)));
    }

    @Override
    public void delete(String id) {
        deleteByStringId(id, intId -> recordDao.deleteById(intId));
    }

    @Override
    public Record findById(String id) {
        return findByStringParam(id, intId -> recordDao.findById(intId)
                .map(EntityMapper::mapEntityRecordToRecord))
                .orElseThrow(() -> new IncorrectDataException("record.not.found"));
    }

    @Override
    public DailySums calculateDailySums(User user, String date) {
        List<Record> records = getRecordsByDate(user, date);
        int energy = 0;
        int protein = 0;
        int fat = 0;
        int carbohydrate = 0;
        int water = 0;
        for (Record record : records) {
            energy += record.calculateEnergy();
            protein += record.calculateProtein();
            fat += record.calculateFat();
            carbohydrate += record.calculateCarbohydrate();
            water += record.calculateWater();
        }

        return DailySums.builder()
                .withSumCarbohydrate(carbohydrate)
                .withSumEnergy(energy)
                .withSumFat(fat)
                .withSumProtein(protein)
                .withSumWater(water)
                .build();
    }

    @Override
    public HomeModel getHomeModel(User user) {
        DailySums dailySums = calculateDailySums(user, LocalDate.now().toString());
        List<LocalDate> dateList = dateProvider.getLastWeek();
        List<String> labels = dateList.stream()
                .map(DATE_TIME_FORMATTER::format)
                .collect(Collectors.toList());

        Map<String, DailySums> weeklyStat = getStatisticsByDates(user, dateList);

        return HomeModel.builder()
                .withDailySums(dailySums)
                .withDailyGoal(HomeModel.calculateDailyGoal(dailySums, user.getUserGoal()))
                .withLabels(labels)
                .withWeeklyStats(weeklyStat)
                .build();
    }

    private Map<String, DailySums> getStatisticsByDates(User user, List<LocalDate> dates) {
        return dates.stream()
                .collect(Collectors.toMap(DATE_TIME_FORMATTER::format, date -> calculateDailySums(user, date.toString())));
    }
}
