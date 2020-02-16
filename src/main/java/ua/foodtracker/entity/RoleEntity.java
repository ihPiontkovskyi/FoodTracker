package ua.foodtracker.entity;

/**
 * Enum which contain user role
 */
public enum RoleEntity {
    USER,
    ADMIN;

    public static final String ERROR_MESSAGE = "There is no such role!";

    public static RoleEntity getRoleById(Integer id) {
        if (id == 2) {
            return ADMIN;
        }
        return USER;
    }

    public Integer getId() {
        return this.ordinal() + 1;
    }
}
