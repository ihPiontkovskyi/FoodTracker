package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.domain.User;

import java.util.regex.Pattern;

@ValidatorClass
public class UserValidator extends AbstractValidator<User> {
    private static final Pattern EMAIL_TEMPLATE = Pattern.compile("^\\w{4,}@\\w+.[a-zA-Z]+$");
    private static final Pattern PASSWORD_TEMPLATE = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public void validate(User user) {
        getMessages().clear();
        if (user == null) {
            putIssue("object", "is.null.message");
            return;
        }
        putIssue("first name", validateName(user.getFirstName()));
        putIssue("last name", validateName(user.getLastName()));
        putIssue("weight", validateInteger(user.getWeight()));
        putIssue("height", validateInteger(user.getHeight()));
        putIssue("birthday", validateDate(user.getBirthday()));
        putIssue("email", validateEmail(user.getEmail()));
        putIssue("password", validatePassword(user.getPassword()));
    }

    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "email.cant.be.empty";
        }
        if (email.length() < MIN_LENGTH || email.length() > MAX_LENGTH) {
            return "email.length.should.be.in.range(3,32)";
        }
        if (!EMAIL_TEMPLATE.matcher(email).matches()) {
            return "email.should.match.email.pattern";
        }
        return null;
    }

    private String validatePassword(String pass) {
        if (pass == null || pass.isEmpty()) {
            return "pass.cant.be.empty";
        }
        if (!PASSWORD_TEMPLATE.matcher(pass).matches()) {
            return "pass.should.match.pass.pattern";
        }
        if (pass.length() < MIN_LENGTH || pass.length() > MAX_LENGTH) {
            return "pass.length.should.be.in.range(3,32)";
        }
        return null;
    }
}
