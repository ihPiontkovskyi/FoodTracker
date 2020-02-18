package ua.foodtracker.validator.impl;

import ua.foodtracker.exception.ValidationException;

import java.time.LocalDate;
import java.util.regex.Pattern;

public abstract class AbstractValidator {

    private static final Pattern TEMPLATE = Pattern.compile("^[a-zA-zа-яА-Я]+$");
    protected static final Integer MIN_LENGTH = 3;
    protected static final Integer MAX_LENGTH = 32;

    protected void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("name.cant.be.empty");
        }
        if (!TEMPLATE.matcher(name).matches()) {
            throw new ValidationException("name.should.contains.letter.only");
        }
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            throw new ValidationException("name.length.should.be.in.range(3,32)");
        }
    }

    protected void validateInteger(Integer value) {
        if (value == null || value < 0) {
            throw new ValidationException("value.cant.be.less.then.0");
        }
    }

    protected void validateDate(LocalDate date) {
        if (date == null || date.compareTo(LocalDate.now()) > 0) {
            throw new ValidationException("cant.set.date");
        }
    }
}
