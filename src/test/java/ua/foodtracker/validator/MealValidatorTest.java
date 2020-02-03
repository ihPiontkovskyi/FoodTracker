package ua.foodtracker.validator;

import org.junit.Test;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.User;
import ua.foodtracker.validator.impl.MealValidator;

import java.sql.Date;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MealValidatorTest {
    private static final User USER = User.builder()
            .withId(1)
            .withEmail("email@email.email")
            .withHeight(10)
            .withPassword("password")
            .withFirstName("Firtsname")
            .withLastName("Lastname")
            .withWeight(10)
            .withBirthday(new Date(System.currentTimeMillis()))
            .build();

    @Test
    public void validateNullShouldHasError() {
        assertTrue(new MealValidator(null, Locale.getDefault()).hasErrors());
    }

    @Test
    public void validateMealShouldntHasError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        assertFalse(new MealValidator(meal, Locale.getDefault()).hasErrors());
    }

    @Test
    public void validateMealShouldHasNameError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("a")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("name"));
    }

    @Test
    public void validateMealShouldHasWeightError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(null)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("weight"));
    }

    @Test
    public void validateMealShouldHasWaterError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(null)
                .withProtein(10)
                .withName("Name")
                .withFat(10)
                .withCarbohydrates(10)
                .build();
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("water"));
    }

    @Test
    public void validateMealShouldHasFatError() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("Name")
                .withFat(-23)
                .withCarbohydrates(10)
                .build();
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("fat"));
    }

    @Test
    public void validateMealShouldHasCarbError() {
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
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("carbohydrates"));
    }
    @Test
    public void validateMealShouldHasNameError2() {
        Meal meal = Meal.builder()
                .withId(1)
                .withWeight(10)
                .withUser(USER)
                .withWater(10)
                .withProtein(10)
                .withName("Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
                .withFat(23)
                .withCarbohydrates(null)
                .build();
        assertTrue(new MealValidator(meal, Locale.getDefault()).getMessages().containsKey("name"));
    }
}

