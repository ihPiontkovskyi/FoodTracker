package ua.foodtracker.entity;

/**
 * Enum which contain user genders
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER,
    NOT_SELECTED;

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
                return NOT_SELECTED;
        }
    }

    public Integer getId() {
        return this.ordinal();
    }
}
