package ua.foodtracker.entity;

import java.sql.Date;

/**
 * Entity class of Record
 */
public class Record {
    private final Integer id;
    private final Meal meal;
    private final Date date;

    public Record(Builder builder) {
        this.id = builder.id;
        this.meal = builder.meal;
        this.date = builder.date;
    }

    public Integer getId() {
        return id;
    }

    public Meal getMeal() {
        return meal;
    }

    public Date getDate() {
        return date;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Meal meal;
        private Date date;

        private Builder(){ }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withMeal(Meal meal) {
            this.meal = meal;
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
