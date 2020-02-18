package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.domain.User;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.validator.UserValidator;

import java.util.regex.Pattern;

@ValidatorClass
public class UserValidatorImpl extends AbstractValidator implements UserValidator {
    private static final Pattern EMAIL_TEMPLATE = Pattern.compile("^\\w{4,}@\\w+.[a-zA-Z]+$");
    private static final Pattern PASSWORD_TEMPLATE = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public void validate(User user) {
        if (user == null) {
            throw new ValidationException("object.is.null");
        }
        validateName(user.getFirstName());
        validateName(user.getLastName());
        validateInteger(user.getWeight());
        validateInteger(user.getHeight());
        validateDate(user.getBirthday());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("email.cant.be.empty");
        }
        if (email.length() < MIN_LENGTH || email.length() > MAX_LENGTH) {
            throw new ValidationException("email.length.should.be.in.range(3,32)");
        }
        if (!EMAIL_TEMPLATE.matcher(email).matches()) {
            throw new ValidationException("email.should.match.email.pattern");
        }
    }

    private void validatePassword(String pass) {
        if (pass == null || pass.isEmpty()) {
            throw new ValidationException("pass.cant.be.empty");
        }
        if (!PASSWORD_TEMPLATE.matcher(pass).matches()) {
            throw new ValidationException("pass.should.match.pass.pattern");
        }
        if (pass.length() < MIN_LENGTH || pass.length() > MAX_LENGTH) {
            throw new ValidationException("pass.length.should.be.in.range(3,32)");
        }
    }
}
