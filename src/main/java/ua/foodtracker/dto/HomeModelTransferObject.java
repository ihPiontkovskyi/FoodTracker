package ua.foodtracker.dto;

import ua.foodtracker.entity.User;
import ua.foodtracker.service.RecordService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeModelTransferObject {
    private static final long WEEK_IN_MILLISECONDS = 604800000L;
    private DailySumsTransferObject dsto;
    private Integer dailyEnergyGoal;
    private Integer dailyProteinGoal;
    private Integer dailyFatGoal;
    private Integer dailyCarbohydratesGoal;
    private Integer dailyWaterGoal;
    private List<String> labels;
    private List<Integer> energyWeeklyStat;
    private List<Integer> proteinWeeklyStat;
    private List<Integer> fatWeeklyStat;
    private List<Integer> carbWeeklyStat;
    private List<Integer> waterWeeklyStat;

    private HomeModelTransferObject(User user, RecordService service) {
        dsto = DailySumsTransferObject.build(service.getPage(user.getId(), new Date(System.currentTimeMillis())));
        calculateDailyGoals(user);
        labels = new ArrayList<>();
        energyWeeklyStat = new ArrayList<>();
        proteinWeeklyStat = new ArrayList<>();
        fatWeeklyStat = new ArrayList<>();
        carbWeeklyStat = new ArrayList<>();
        waterWeeklyStat = new ArrayList<>();
        setStatistics(user, service);
    }

    private void calculateDailyGoals(User user) {
        dailyEnergyGoal = Math.min((int) (((double) dsto.getSumEnergy() / user.getUserGoal().getDailyEnergyGoal()) * 100), 100);
        dailyProteinGoal = Math.min((int) (((double) dsto.getSumProtein() / user.getUserGoal().getDailyProteinGoal()) * 100), 100);
        dailyFatGoal = Math.min((int) (((double) dsto.getSumFat() / user.getUserGoal().getDailyFatGoal()) * 100), 100);
        dailyCarbohydratesGoal = Math.min((int) (((double) dsto.getSumCarbohydrates() / user.getUserGoal().getDailyCarbohydrateGoal()) * 100), 100);
        dailyWaterGoal = Math.min((int) (((double) dsto.getSumWater() / user.getUserGoal().getDailyWaterGoal()) * 100), 100);
    }

    private void setStatistics(User user, RecordService service) {
        List<Date> dateList = new ArrayList<>();
        LocalDate start = LocalDate.parse(new Date(System.currentTimeMillis() - WEEK_IN_MILLISECONDS).toString());
        LocalDate end = LocalDate.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd.MM");
        while (!start.isAfter(end)) {
            labels.add(dtf2.format(start));
            dateList.add(Date.valueOf(start));
            start = start.plusDays(1);
        }
        for (Date date : dateList) {
            DailySumsTransferObject ddsto = DailySumsTransferObject.build(service.getPage(user.getId(), date));
            energyWeeklyStat.add(ddsto.getSumEnergy());
            proteinWeeklyStat.add(ddsto.getSumProtein());
            fatWeeklyStat.add(ddsto.getSumFat());
            carbWeeklyStat.add(ddsto.getSumCarbohydrates());
            waterWeeklyStat.add(ddsto.getSumWater());
        }
    }

    public DailySumsTransferObject getDsto() {
        return dsto;
    }

    public Integer getDailyEnergyGoal() {
        return dailyEnergyGoal;
    }

    public Integer getDailyProteinGoal() {
        return dailyProteinGoal;
    }

    public Integer getDailyFatGoal() {
        return dailyFatGoal;
    }

    public Integer getDailyCarbohydratesGoal() {
        return dailyCarbohydratesGoal;
    }

    public Integer getDailyWaterGoal() {
        return dailyWaterGoal;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Integer> getEnergyWeeklyStat() {
        return energyWeeklyStat;
    }

    public List<Integer> getProteinWeeklyStat() {
        return proteinWeeklyStat;
    }

    public List<Integer> getFatWeeklyStat() {
        return fatWeeklyStat;
    }

    public List<Integer> getCarbWeeklyStat() {
        return carbWeeklyStat;
    }

    public List<Integer> getWaterWeeklyStat() {
        return waterWeeklyStat;
    }

    public static HomeModelTransferObject build(User user, RecordService service) {
        return new HomeModelTransferObject(user, service);
    }


}
