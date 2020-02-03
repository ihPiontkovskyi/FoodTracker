package ua.foodtracker.validator.impl;

import org.apache.log4j.Logger;
import ua.foodtracker.validator.Validator;

import java.sql.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public abstract class AbstractValidator implements Validator {
    private static final Logger LOGGER = Logger.getLogger(AbstractValidator.class);

    private static final String BUNDLE_PATH = "locale/validate_messages";
    private static final Pattern TEMPLATE = Pattern.compile("^[a-zA-zа-яА-Я]+$");
    protected static final Integer MIN_LENGTH = 3;
    protected static final Integer MAX_LENGTH = 32;

    protected Locale locale;
    private Map<String, String> errMessages;
    protected ResourceBundle bundle;

    protected AbstractValidator(Locale locale) {
        this.locale = locale;
        bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
        errMessages = new HashMap<>();
    }

    @Override
    public boolean hasErrors() {
        return errMessages.size() != 0;
    }

    @Override
    public Map<String, String> getMessages() {
        return errMessages;
    }

    @Override
    public void putIssue(String key, String message) {
        if (key == null || message == null) {
            return;
        }
        LOGGER.debug(String.format("%s translation %s", key, locale));
        LOGGER.debug(String.format((bundle != null) ? "bundle %s" : "bundle for %s is empty", locale));

        if (bundle != null) {
            LOGGER.debug(String.format("Put message %s", message));
            errMessages.put(key, message);
        }
    }

    protected String validateName(String name) {
        if (name == null || name.isEmpty()) {
            return bundle.getString("name.cant.be.empty");
        }
        if (!TEMPLATE.matcher(name).matches()) {
            return bundle.getString("name.should.contains.letter.only");
        }
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            return bundle.getString("name.length.should.be.in.range(3,32)");
        }
        return null;
    }

    protected String validateInteger(Integer value) {
        if (value == null || value < 0) {
            return bundle.getString("value.cant.be.less.then.0");
        }
        return null;
    }

    protected String validateDate(Date date) {
        if (date == null || date.compareTo(new Date(System.currentTimeMillis())) >= 0) {
            return bundle.getString("cant.set.date");
        }
        return null;
    }
}
