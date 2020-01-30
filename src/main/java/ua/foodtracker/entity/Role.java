package ua.foodtracker.entity;

/**
 * Enum which contain user role
 */
public enum Role {
    USER,
    ADMIN;


    public static final String ERROR_MESSAGE = "There is no such role!";

    public static Role getGenderById(Integer id) {
        switch (id) {
            case 1:
                return USER;
            case 2:
                return ADMIN;
            default:
                throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public Integer getId() {
        return this.ordinal();
    }
}
