package ua.foodtracker.validator.impl;

import ua.foodtracker.entity.User;

import java.util.Locale;
import java.util.regex.Pattern;

public class UserValidator extends AbstractValidator {
    private static final Pattern EMAIL_TEMPLATE = Pattern.compile("^\\w{4,}@\\w+.[a-zA-Z]+$");
    private static final Pattern PASSWORD_TEMPLATE = Pattern.compile("^[a-zA-Z0-9]+$");

    public UserValidator(User user, Locale locale) {
        super(locale);
        if (user == null) {
            putIssue("object", bundle.getString("is.null.message"));
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
            return bundle.getString("email.cant.be.empty");
        }
        if (!EMAIL_TEMPLATE.matcher(email).matches()) {
            return bundle.getString("email.should.match.email.pattern");
        }
        if (email.length() < MIN_LENGTH || email.length() > MAX_LENGTH) {
            return bundle.getString("email.length.should.be.in.range(3,32)");
        }
        return null;
    }

    private String validatePassword(String pass) {
        if (pass == null || pass.isEmpty()) {
            return bundle.getString("pass.cant.be.empty");
        }
        if (!PASSWORD_TEMPLATE.matcher(pass).matches()) {
            return bundle.getString("pass.should.match.pass.pattern");
        }
        if (pass.length() < MIN_LENGTH || pass.length() > MAX_LENGTH) {
            return bundle.getString("pass.length.should.be.in.range(3,32)");
        }
        return null;
    }
}
