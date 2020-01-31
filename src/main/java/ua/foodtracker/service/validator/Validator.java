package ua.foodtracker.service.validator;

import java.util.Map;

public interface Validator {
    boolean hasErrors();

    Map<String, String> getMessages();

    void putIssue(String key, String message);
}
