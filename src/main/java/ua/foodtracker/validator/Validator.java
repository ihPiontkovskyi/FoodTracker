package ua.foodtracker.validator;

import java.util.Locale;
import java.util.Map;

public interface Validator<E> {
    boolean hasErrors();

    Map<String, String> getMessages();

    void putIssue(String key, String message);

    void validate(E entity);

    void setLocale(Locale locale);
}
