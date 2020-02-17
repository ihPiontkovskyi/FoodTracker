package ua.foodtracker.entity;

import java.time.LocalDate;

/**
 * Entity class of Record
 */
public class RecordEntity {
    private final Integer id;
    private final MealEntity mealEntity;
    private final LocalDate date;
    private final Integer userId;
    private final Integer weight;

    public RecordEntity(Builder builder) {
        this.id = builder.id;
        this.mealEntity = builder.mealEntity;
        this.date = builder.date;
        this.userId = builder.userId;
        this.weight = builder.weight;
    }

    public Integer getId() {
        return id;
    }

    public MealEntity getMealEntity() {
        return mealEntity;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private MealEntity mealEntity;
        private LocalDate date;
        private Integer userId;
        private Integer weight;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withMeal(MealEntity mealEntity) {
            this.mealEntity = mealEntity;
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

        public RecordEntity build() {
            return new RecordEntity(this);
        }
    }
}
