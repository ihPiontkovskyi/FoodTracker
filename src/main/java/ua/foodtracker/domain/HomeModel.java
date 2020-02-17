package ua.foodtracker.domain;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeModel {
    private static final int PERCENTAGE = 100;
    private static final int MAX_PERCENTAGE = 100;

    private DailySums dailySums;
    private List<String> labels;
    private Map<String, DailySums> weeklyStats;
    private DailyGoal dailyGoal;

    public HomeModel(Builder builder) {
        this.dailySums = builder.dailySums;
        this.labels = builder.labels;
        this.weeklyStats = builder.weeklyStats;
        this.dailyGoal = builder.dailyGoal;
    }

    public List<Integer> getWeeklyEnergyStat() {
        return getListByFunction(DailySums::getSumEnergy);
    }

    public List<Integer> getWeeklyFatStat() {
        return getListByFunction(DailySums::getSumFat);
    }

    public List<Integer> getWeeklyWaterStat() {
        return getListByFunction(DailySums::getSumWater);
    }

    public List<Integer> getWeeklyCarbohydrateStat() {
        return getListByFunction(DailySums::getSumCarbohydrate);
    }

    public List<Integer> getWeeklyProteinStat() {
        return getListByFunction(DailySums::getSumProtein);
    }

    public DailySums getDailySums() {
        return dailySums;
    }

    public List<String> getLabels() {
        return labels;
    }

    public DailyGoal getDailyGoal() {
        return dailyGoal;
    }

    private List<Integer> getListByFunction(Function<? super DailySums, ? extends Integer> function) {
        return weeklyStats
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(function)
                .collect(Collectors.toList());
    }

    public static DailyGoal calculateDailyGoal(DailySums dailySums, UserGoal userGoal) {
        return DailyGoal.builder()
                .withDailyCarbohydratesGoal(getPercentage(dailySums.getSumCarbohydrate(), userGoal.getDailyCarbohydrateGoal()))
                .withDailyEnergyGoal(getPercentage(dailySums.getSumEnergy(), userGoal.getDailyEnergyGoal()))
                .withDailyFatGoal(getPercentage(dailySums.getSumFat(), userGoal.getDailyFatGoal()))
                .withDailyProteinGoal(getPercentage(dailySums.getSumProtein(), userGoal.getDailyProteinGoal()))
                .withDailyWaterGoal(getPercentage(dailySums.getSumWater(), userGoal.getDailyWaterGoal()))
                .build();
    }

    private static int getPercentage(double dailySum, int dailyGoal) {
        return Math.min((int) ((dailySum / dailyGoal) * PERCENTAGE), MAX_PERCENTAGE);
    }

    public static class DailyGoal {
        private final int dailyEnergyGoal;
        private final int dailyProteinGoal;
        private final int dailyFatGoal;
        private final int dailyCarbohydratesGoal;
        private final int dailyWaterGoal;

        public int getDailyEnergyGoal() {
            return dailyEnergyGoal;
        }

        public int getDailyProteinGoal() {
            return dailyProteinGoal;
        }

        public int getDailyFatGoal() {
            return dailyFatGoal;
        }

        public int getDailyCarbohydratesGoal() {
            return dailyCarbohydratesGoal;
        }

        public int getDailyWaterGoal() {
            return dailyWaterGoal;
        }

        public DailyGoal(Builder builder) {
            this.dailyEnergyGoal = builder.dailyEnergyGoal;
            this.dailyProteinGoal = builder.dailyProteinGoal;
            this.dailyFatGoal = builder.dailyFatGoal;
            this.dailyCarbohydratesGoal = builder.dailyCarbohydratesGoal;
            this.dailyWaterGoal = builder.dailyWaterGoal;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private int dailyEnergyGoal;
            private int dailyProteinGoal;
            private int dailyFatGoal;
            private int dailyCarbohydratesGoal;
            private int dailyWaterGoal;

            public Builder withDailyEnergyGoal(int dailyEnergyGoal) {
                this.dailyEnergyGoal = dailyEnergyGoal;
                return this;
            }

            public Builder withDailyProteinGoal(int dailyProteinGoal) {
                this.dailyProteinGoal = dailyProteinGoal;
                return this;
            }

            public Builder withDailyFatGoal(int dailyFatGoal) {
                this.dailyFatGoal = dailyFatGoal;
                return this;
            }

            public Builder withDailyCarbohydratesGoal(int dailyCarbohydratesGoal) {
                this.dailyCarbohydratesGoal = dailyCarbohydratesGoal;
                return this;
            }

            public Builder withDailyWaterGoal(int dailyWaterGoal) {
                this.dailyWaterGoal = dailyWaterGoal;
                return this;
            }

            public DailyGoal build() {
                return new DailyGoal(this);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DailySums dailySums;
        private List<String> labels;
        private Map<String, DailySums> weeklyStats;
        private DailyGoal dailyGoal;

        public Builder withDailySums(DailySums dailySums) {
            this.dailySums = dailySums;
            return this;
        }

        public Builder withLabels(List<String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder withWeeklyStats(Map<String, DailySums> weeklyStats) {
            this.weeklyStats = weeklyStats;
            return this;
        }

        public Builder withDailyGoal(DailyGoal dailyGoal) {
            this.dailyGoal = dailyGoal;
            return this;
        }

        public HomeModel build() {
            return new HomeModel(this);
        }
    }
}