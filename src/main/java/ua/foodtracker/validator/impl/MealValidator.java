package ua.foodtracker.validator.impl;

import ua.foodtracker.annotation.ValidatorClass;
import ua.foodtracker.entity.Role;
import ua.foodtracker.service.domain.Meal;
import ua.foodtracker.service.domain.User;
import ua.foodtracker.validator.Validator;

@ValidatorClass
public class MealValidator extends AbstractValidator<Meal> {

    @Override
    public void validate(Meal meal) {
        getMessages().clear();
        if (meal == null) {
            putIssue("object", "is.null.message");
            return;
        }
        putIssue("name", validateName(meal.getName()));
        putIssue("protein", validateInteger(meal.getProtein()));
        putIssue("carbohydrates", validateInteger(meal.getCarbohydrate()));
        putIssue("fat", validateInteger(meal.getFat()));
        putIssue("weight", validateInteger(meal.getWeight()));
        putIssue("water", validateInteger(meal.getWater()));
        if (meal.getUser() != null && meal.getUser().getRole() != Role.ADMIN) {
            Validator<User> userValidator = new UserValidator();
            userValidator.validate(meal.getUser());
            userValidator.getMessages().forEach((key, value) -> putIssue("user by rawMeal: " + key, value));
        }
    }
}
