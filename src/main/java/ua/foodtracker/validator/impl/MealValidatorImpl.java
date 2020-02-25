package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.exception.ValidationException;

@ValidatorClass
public class MealValidatorImpl extends AbstractValidator implements ua.foodtracker.validator.MealValidator {

    @Override
    public void validate(Meal meal) {
        if (meal == null) {
            throw new ValidationException("object.is.null");
        }
        validateName(meal.getName());
        validateInteger(meal.getProtein());
        validateInteger(meal.getCarbohydrate());
        validateInteger(meal.getFat());
        validateInteger(meal.getWeight());
        validateInteger(meal.getWater());
    }
}
