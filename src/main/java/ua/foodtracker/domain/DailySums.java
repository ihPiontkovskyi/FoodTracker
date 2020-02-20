package ua.foodtracker.domain;

/**
 * Class stores information about records by a specific date
 */
public final class DailySums {
    private final int sumEnergy;
    private final int sumProtein;
    private final int sumFat;
    private final int sumCarbohydrate;
    private final int sumWater;

    private DailySums(Builder builder) {
        this.sumEnergy = builder.sumEnergy;
        this.sumProtein = builder.sumProtein;
        this.sumFat = builder.sumFat;
        this.sumCarbohydrate = builder.sumCarbohydrate;
        this.sumWater = builder.sumWater;
    }


    public int getSumEnergy() {
        return sumEnergy;
    }

    public int getSumProtein() {
        return sumProtein;
    }

    public int getSumFat() {
        return sumFat;
    }

    public int getSumCarbohydrate() {
        return sumCarbohydrate;
    }

    public int getSumWater() {
        return sumWater;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int sumEnergy;
        private int sumProtein;
        private int sumFat;
        private int sumCarbohydrate;
        private int sumWater;

        public Builder withSumEnergy(int sumEnergy) {
            this.sumEnergy = sumEnergy;
            return this;
        }

        public Builder withSumProtein(int sumProtein) {
            this.sumProtein = sumProtein;
            return this;
        }

        public Builder withSumFat(int sumFat) {
            this.sumFat = sumFat;
            return this;
        }

        public Builder withSumCarbohydrate(int sumCarbohydrate) {
            this.sumCarbohydrate = sumCarbohydrate;
            return this;
        }

        public Builder withSumWater(int sumWater) {
            this.sumWater = sumWater;
            return this;
        }

        public DailySums build() {
            return new DailySums(this);
        }
    }
}
