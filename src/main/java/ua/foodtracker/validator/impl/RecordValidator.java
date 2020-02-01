package ua.foodtracker.validator.impl;

import ua.foodtracker.entity.Record;

import java.util.Locale;

public class RecordValidator extends AbstractValidator {
    public RecordValidator(Record record, Locale locale) {
        super(locale);
        if (record == null) {
            putIssue("object", bundle.getString("is.null.message"));
            return;
        }
        putIssue("record date", validateDate(record.getDate()));
        new MealValidator(record.getMeal(), locale)
                .getMessages().forEach((key, value) -> putIssue("recording meal: " + key, value));
    }
}
