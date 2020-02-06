package ua.foodtracker.entity;

import java.sql.Date;

/**
 * Entity class of Record
 */
public class RecordEntity {
    private final Integer id;
    private final MealEntity mealEntity;
    private final Date date;
    private final Integer userId;

    public RecordEntity(Builder builder) {
        this.id = builder.id;
        this.mealEntity = builder.mealEntity;
        this.date = builder.date;
        this.userId = builder.userId;
    }

    public Integer getId() {
        return id;
    }

    public MealEntity getMealEntity() {
        return mealEntity;
    }

    public Date getDate() {
        return date;
    }

    public Integer getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private MealEntity mealEntity;
        private Date date;
        private Integer userId;

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

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public RecordEntity build() {
            return new RecordEntity(this);
        }
    }
}