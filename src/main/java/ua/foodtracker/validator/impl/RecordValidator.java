package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.service.entity.RawMeal;
import ua.foodtracker.service.entity.RawRecord;
import ua.foodtracker.validator.Validator;

@ValidatorClass
public class RecordValidator extends AbstractValidator<RawRecord> {

    @Override
    public void validate(RawRecord record) {
        getMessages().clear();
        if (record == null) {
            putIssue("object", "is.null.message");
            return;
        }
        putIssue("record date", validateDate(record.getDate()));
        Validator<RawMeal> mealValidator = new MealValidator();
        mealValidator.validate(record.getMeal());
        mealValidator.getMessages().forEach((key, value) -> putIssue("recording meal: " + key, value));
    }
}
