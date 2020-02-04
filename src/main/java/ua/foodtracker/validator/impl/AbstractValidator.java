package ua.foodtracker.validator.impl;

import org.apache.log4j.Logger;
import ua.foodtracker.validator.Validator;

import java.sql.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractValidator<E> implements Validator<E> {
    private static final Logger LOGGER = Logger.getLogger(AbstractValidator.class);

    private static final Pattern TEMPLATE = Pattern.compile("^[a-zA-zа-яА-Я]+$");
    protected static final Integer MIN_LENGTH = 3;
    protected static final Integer MAX_LENGTH = 32;

    protected Locale locale;
    private Map<String, String> errMessages;

    protected AbstractValidator() {
        this.locale = Locale.getDefault();
        this.errMessages = new HashMap<>();
    }

    @Override
    public boolean hasErrors() {
        return errMessages.size() != 0;
    }

    @Override
    public Map<String, String> getMessages() {
        return errMessages;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public void putIssue(String key, String message) {
        if (key == null || message == null) {
            return;
        }
        LOGGER.debug(String.format("Put message %s", message));
        errMessages.put(key, message);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    protected String validateName(String name) {
        if (name == null || name.isEmpty()) {
            return "name.cant.be.empty";
        }
        if (!TEMPLATE.matcher(name).matches()) {
            return "name.should.contains.letter.only";
        }
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            return "name.length.should.be.in.range(3,32)";
        }
        return null;
    }

    protected String validateInteger(Integer value) {
        if (value == null || value < 0) {
            return "value.cant.be.less.then.0";
        }
        return null;
    }

    protected String validateDate(Date date) {
        if (date == null || date.compareTo(new Date(System.currentTimeMillis())) >= 0) {
            return "cant.set.date";
        }
        return null;
    }
}
