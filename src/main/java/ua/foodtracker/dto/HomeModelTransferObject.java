package ua.foodtracker.dto;

import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.domain.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeModelTransferObject {
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
        dsto = DailySumsTransferObject.build(service.getRecordsByDate(user.getId(), LocalDate.now()));
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
        dailyEnergyGoal = Math.min((int) (((double) dsto.getSumEnergy() / user.getUserGoalEntity().getDailyEnergyGoal()) * 100), 100);
        dailyProteinGoal = Math.min((int) (((double) dsto.getSumProtein() / user.getUserGoalEntity().getDailyProteinGoal()) * 100), 100);
        dailyFatGoal = Math.min((int) (((double) dsto.getSumFat() / user.getUserGoalEntity().getDailyFatGoal()) * 100), 100);
        dailyCarbohydratesGoal = Math.min((int) (((double) dsto.getSumCarbohydrates() / user.getUserGoalEntity().getDailyCarbohydrateGoal()) * 100), 100);
        dailyWaterGoal = Math.min((int) (((double) dsto.getSumWater() / user.getUserGoalEntity().getDailyWaterGoal()) * 100), 100);
    }

    private void setStatistics(User user, RecordService service) {
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate start = LocalDate.now().minusDays(7);
        LocalDate end = LocalDate.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd.MM");
        while (!start.isAfter(end)) {
            labels.add(dtf2.format(start));
            dateList.add(start);
            start = start.plusDays(1);
        }
        for (LocalDate date : dateList) {
            DailySumsTransferObject ddsto = DailySumsTransferObject.build(service.getRecordsByDate(user.getId(), date));
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
