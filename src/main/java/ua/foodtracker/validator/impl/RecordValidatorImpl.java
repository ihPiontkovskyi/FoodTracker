package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.domain.Record;
import ua.foodtracker.exception.ValidationException;

@ValidatorClass
public class RecordValidatorImpl extends AbstractValidator implements ua.foodtracker.validator.RecordValidator {

    @Override
    public void validate(Record record) {
        if (record == null) {
            throw new ValidationException("object.is.null");
        }
        validateDate(record.getDate());
        validateInteger(record.getWeight());
    }
}
