package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.validator.Validator;

@ValidatorClass
public class RecordValidator extends AbstractValidator<Record> {

    @Override
    public void validate(Record record) {
        getMessages().clear();
        if (record == null) {
            putIssue("object", "is.null.message");
            return;
        }
        putIssue("record date", validateDate(record.getDate()));
        Validator<Meal> mealValidator = new MealValidator();
        mealValidator.validate(record.getMeal());
        mealValidator.getMessages().forEach((key, value) -> putIssue("recording meal: " + key, value));
    }
}
