package ua.foodtracker.validator;

import ua.foodtracker.domain.User;

public interface UserValidator extends Validator<User> {
    void validatePassword(String pass);
}
