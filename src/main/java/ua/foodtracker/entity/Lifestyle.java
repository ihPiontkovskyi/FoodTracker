package ua.foodtracker.entity;

/**
 * Enum which contain user lifestyles
 */
public enum Lifestyle {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    NOT_SELECTED(1.0);

    public static final String ERROR_MESSAGE = "There is no such lifestyle!";

    private Double coefficient;

    private Lifestyle(Double coefficient) {
        this.coefficient = coefficient;
    }

    public static Lifestyle getLifestyleById(Integer id) {
        switch (id) {
            case 1:
                return SEDENTARY;
            case 2:
                return LIGHTLY_ACTIVE;
            case 3:
                return ACTIVE;
            case 4:
                return VERY_ACTIVE;
            default:
                return NOT_SELECTED;
        }
    }

    public Integer getId() {
        return this.ordinal();
    }

    public Double getCoefficient() {
        return coefficient;
    }
}
