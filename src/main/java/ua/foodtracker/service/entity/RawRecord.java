package ua.foodtracker.service.entity;

import java.time.LocalDate;

public class RawRecord {
    private Integer id;
    private RawMeal meal;
    private LocalDate date;
    private Integer userId;

    public RawRecord(Builder builder) {
        this.id = builder.id;
        this.meal = builder.meal;
        this.date = builder.date;
        this.userId = builder.userId;
    }

    public Integer getId() {
        return id;
    }

    public RawMeal getMeal() {
        return meal;
    }

    public LocalDate getDate() {
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
        private RawMeal meal;
        private LocalDate date;
        private Integer userId;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withMeal(RawMeal meal) {
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

        public RawRecord build() {
            return new RawRecord(this);
        }
    }
}
