package ua.foodtracker.entity;

import java.sql.Date;

/**
 * Entity class of Record
 */
public class Record {
    private final Integer id;
    private final Integer userId;
    private final Integer mealId;
    private final Date date;

    public Record(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.mealId = builder.mealId;
        this.date = builder.date;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getMealId() {
        return mealId;
    }

    public Date getDate() {
        return date;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        private Integer id;
        private Integer userId;
        private Integer mealId;
        private Date date;

        private Builder(){ }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder withMeal(Integer mealId) {
            this.mealId = mealId;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Record build() {
            return new Record(this);
        }
    }
}
