package ua.foodtracker.entity;

/**
 * Enum which contain user genders
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static final String ERROR_MESSAGE = "There is no such gender!";

    public static Gender getGenderById(Integer id) {
        switch (id) {
            case 1:
                return MALE;
            case 2:
                return FEMALE;
            case 3:
                return OTHER;
            default:
                throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public Integer getId() {
        return this.ordinal();
    }
}
