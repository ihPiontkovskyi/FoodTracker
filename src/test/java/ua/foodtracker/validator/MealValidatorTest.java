package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Test;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.validator.impl.MealValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MealValidatorTest {
    private static final User ADMIN = User.builder()
            .withId(1)
            .withEmail("email@email.email")
            .withHeight(10)
            .withPassword("password")
            .withFirstName("Firtsname")
            .withLastName("Lastname")
            .withWeight(10)
            .withBirthday(LocalDate.now())
            .withRole(Role.ADMIN)
            .build();
    private static final User USER = User.builder()
            .withId(1)
            .withEmail(null)
            .withHeight(10)
            .withPassword("password")
            .withFirstName("Firtsname")
            .withLastName("Lastname")
            .withWeight(10)
            .withBirthday(LocalDate.now())
            .withRole(Role.USER)
            .build();

    private Validator<Meal> mealValidator;

    @Before
    public void initValidator() {
        mealValidator = new MealValidator();
    }

    @Test
    public void validateNullShouldHasError() {
        mealValidator.validate(null);
        assertTrue(mealValidator.hasErrors());
    }

    @Test
    public void validateMealShouldntHasError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(ADMIN)
                .withWater(10)
                .withProtein(10)
                .withName("name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        mealValidator.validate(meal);
        assertFalse(mealValidator.hasErrors());
    }

    @Test
    public void validateMealShouldHasNameError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(ADMIN)
                .withWater(10)
                .withProtein(10)
                .withName("a")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("name"));
    }

    @Test
    public void validateMealShouldHasWeightError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(null)
                .withUser(ADMIN)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("weight"));
    }

    @Test
    public void validateMealShouldHasWaterError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(ADMIN)
                .withWater(null)
                .withProtein(10)
                .withName("Name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("water"));
    }

    @Test
    public void validateMealShouldHasFatError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(ADMIN)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(-23)
                .withCarbohydrates(10)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("fat"));
    }

    @Test
    public void validateMealShouldHasCarbError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(ADMIN)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("carbohydrates"));
    }

    @Test
    public void validateMealShouldHasNameError2() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(null)
                .withWater(10)
                .withProtein(10)
                .withName("Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("name"));
    }

    @Test
    public void validateMealShouldHasNameError3() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(null)
                .withWater(10)
                .withProtein(10)
                .withName("")
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("name"));
    }

    @Test
    public void validateMealShouldHasNameError4() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(null)
                .withWater(10)
                .withProtein(10)
                .withName(null)
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.getMessages().containsKey("name"));
    }

    @Test
    public void validateMealShouldHasUserEmailError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        mealValidator.validate(meal);
        assertTrue(mealValidator.hasErrors());
    }
}
