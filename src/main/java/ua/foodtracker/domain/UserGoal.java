package ua.foodtracker.domain;

/**
 * Domain class of User goal
 */

public class UserGoal {
    private final Integer id;
    private final Integer dailyEnergyGoal;
    private final Integer dailyFatGoal;
    private final Integer dailyProteinGoal;
    private final Integer dailyCarbohydrateGoal;
    private final Integer dailyWaterGoal;

    public UserGoal(Builder builder) {
        this.id = builder.id;
        this.dailyEnergyGoal = builder.dailyEnergyGoal;
        this.dailyFatGoal = builder.dailyFatGoal;
        this.dailyProteinGoal = builder.dailyProteinGoal;
        this.dailyCarbohydrateGoal = builder.dailyCarbohydrateGoal;
        this.dailyWaterGoal = builder.dailyWaterGoal;
    }

    public Integer getDailyEnergyGoal() {
        return dailyEnergyGoal;
    }

    public Integer getDailyFatGoal() {
        return dailyFatGoal;
    }

    public Integer getDailyProteinGoal() {
        return dailyProteinGoal;
    }

    public Integer getDailyCarbohydrateGoal() {
        return dailyCarbohydrateGoal;
    }

    public Integer getDailyWaterGoal() {
        return dailyWaterGoal;
    }

    public Integer getId() {
        return id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer dailyEnergyGoal;
        private Integer dailyFatGoal;
        private Integer dailyProteinGoal;
        private Integer dailyCarbohydrateGoal;
        private Integer dailyWaterGoal;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withDailyEnergyGoal(Integer dailyEnergyGoal) {
            this.dailyEnergyGoal = dailyEnergyGoal;
            return this;
        }

        public Builder withDailyFatGoal(Integer dailyFatGoal) {
            this.dailyFatGoal = dailyFatGoal;
            return this;
        }

        public Builder withDailyProteinGoal(Integer dailyProteinGoal) {
            this.dailyProteinGoal = dailyProteinGoal;
            return this;
        }

        public Builder withDailyCarbohydrateGoal(Integer dailyCarbohydrateGoal) {
            this.dailyCarbohydrateGoal = dailyCarbohydrateGoal;
            return this;
        }

        public Builder withDailyWaterGoal(Integer dailyWaterGoal) {
            this.dailyWaterGoal = dailyWaterGoal;
            return this;
        }

        public UserGoal build() {
            return new UserGoal(this);
        }
    }
}
