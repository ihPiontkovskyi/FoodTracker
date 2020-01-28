package ua.foodtracker.entity;

public class Lifestyle {
    private final Integer id;
    private final String description;
    private final String name;
    private final Double energyCoefficient;

    public Lifestyle(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.name = builder.name;
        this.energyCoefficient = builder.energyCoefficient;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getEnergyCoefficient() {
        return energyCoefficient;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String description;
        private String name;
        private Double energyCoefficient;

        private Builder(){}

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEnergyCoefficient(Double energyCoefficient) {
            this.energyCoefficient = energyCoefficient;
            return this;
        }

        public Lifestyle build() {
            return new Lifestyle(this);
        }
    }
}
