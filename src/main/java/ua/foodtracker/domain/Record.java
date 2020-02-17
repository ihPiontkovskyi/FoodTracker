package ua.foodtracker.domain;

import java.time.LocalDate;

public class Record {
    private Integer id;
    private Meal meal;
    private LocalDate date;
    private Integer userId;
    private Integer weight;

    public Record(Builder builder) {
        this.id = builder.id;
        this.meal = builder.meal;
        this.date = builder.date;
        this.userId = builder.userId;
        this.weight = builder.weight;
    }

    public Integer getId() {
        return id;
    }

    public Meal getMeal() {
        return meal;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer calculateProtein() {
        return (int) (meal.getProtein() * (((double) weight) / meal.getWeight()));
    }

    public Integer calculateCarbohydrate() {
        return (int) (meal.getCarbohydrate() * (((double) weight) / meal.getWeight()));
    }

    public Integer calculateFat() {
        return (int) (meal.getFat() * (((double) weight) / meal.getWeight()));
    }

    public Integer calculateWater() {
        return (int) (meal.getWater() * (((double) weight) / meal.getWeight()));
    }

    public Integer calculateEnergy() {
        return (int) (meal.calculateEnergy() * (((double) weight) / meal.getWeight()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Meal meal;
        private LocalDate date;
        private Integer userId;
        private Integer weight;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withMeal(Meal meal) {
            this.meal = meal;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder withWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public Record build() {
            return new Record(this);
        }
    }
}
