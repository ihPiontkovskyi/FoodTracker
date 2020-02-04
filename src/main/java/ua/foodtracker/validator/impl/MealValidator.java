package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.dao.entity.Role;
import ua.foodtracker.raw.type.entity.RawMeal;
import ua.foodtracker.raw.type.entity.RawUser;
import ua.foodtracker.validator.Validator;

@ValidatorClass
public class MealValidator extends AbstractValidator<RawMeal> {

    @Override
    public void validate(RawMeal rawMeal) {
        getMessages().clear();
        if (rawMeal == null) {
            putIssue("object", "is.null.message");
            return;
        }
        putIssue("name", validateName(rawMeal.getName()));
        putIssue("protein", validateInteger(rawMeal.getProtein()));
        putIssue("carbohydrates", validateInteger(rawMeal.getCarbohydrate()));
        putIssue("fat", validateInteger(rawMeal.getFat()));
        putIssue("weight", validateInteger(rawMeal.getWeight()));
        putIssue("water", validateInteger(rawMeal.getWater()));
        if (rawMeal.getUser().getRole() != Role.ADMIN) {
            Validator<RawUser> userValidator = new UserValidator();
            userValidator.validate(rawMeal.getUser());
            userValidator.getMessages().forEach((key, value) -> putIssue("user by rawMeal: " + key, value));
        }
    }
}
