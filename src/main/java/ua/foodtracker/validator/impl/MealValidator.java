package ua.foodtracker.validator.impl;

import ua.foodtracker.entity.Meal;

import java.util.Locale;

public class MealValidator extends AbstractValidator {

    public MealValidator(Meal meal, Locale locale) {
        super(locale);
        if (meal == null) {
            putIssue("object", bundle.getString("is.null.message"));
            return;
        }
        putIssue("name", validateName(meal.getName()));
        putIssue("protein", validateInteger(meal.getProtein()));
        putIssue("carbohydrates", validateInteger(meal.getCarbohydrate()));
        putIssue("fat", validateInteger(meal.getFat()));
        putIssue("weight", validateInteger(meal.getWeight()));
        putIssue("water", validateInteger(meal.getWater()));
        new UserValidator(meal.getUser(), locale)
                .getMessages().forEach((key, value) -> putIssue("user by meal: " + key, value));
    }
}
