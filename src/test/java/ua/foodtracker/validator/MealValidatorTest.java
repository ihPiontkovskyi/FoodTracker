package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.validator.impl.MealValidatorImpl;

import java.time.LocalDate;

public class MealValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    private MealValidator mealValidator;

    @Before
    public void initValidator() {
        mealValidator = new MealValidatorImpl();
    }

    @Test
    public void validateNullShouldHasError() {
        exception.expect(ValidationException.class);
        exception.expectMessage("object.is.null");
        mealValidator.validate(null);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.length.should.be.in.range(3,32)");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.length.should.be.in.range(3,32)");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.cant.be.empty");
        mealValidator.validate(meal);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.cant.be.empty");
        mealValidator.validate(meal);
    }

}
