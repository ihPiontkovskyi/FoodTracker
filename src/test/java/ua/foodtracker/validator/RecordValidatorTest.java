package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Test;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;
import ua.foodtracker.validator.impl.RecordValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordValidatorTest {
    private static final User USER = User.builder()
            .withId(1)
            .withEmail("email@email.email")
            .withHeight(10)
            .withPassword("password")
            .withFirstName("Firtsname")
            .withLastName("Lastname")
            .withWeight(10)
            .withBirthday(LocalDate.now())
            .build();
    private static final Meal MEAL = Meal.builder()
            .withId(1)
            .withWeight(10)
            .withUser(USER)
            .withWater(10)
            .withProtein(10)
            .withName("name")
            .withFat(10)
            .withCarbohydrates(10)
            .build();

    private Validator<Record> recordValidator;

    @Before
    public void initValidator() {
        recordValidator = new RecordValidator();
    }

    @Test
    public void nullRecordValidateShouldHasError() {
        recordValidator.validate(null);
        assertTrue(recordValidator.hasErrors());
    }

    @Test
    public void recordValidateShouldntHasErrors() {
        Record record = Record.builder()
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(LocalDate.now())
                .build();
        recordValidator.validate(record);
        assertFalse(recordValidator.hasErrors());
    }

    @Test
    public void recordValidateShouldHasDateValidateError() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(null)
                .build();
        recordValidator.validate(record);
        assertTrue(recordValidator.hasErrors());
    }

    @Test
    public void recordValidateShouldHasDateValidateError2() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(LocalDate.now().plusDays(1))
                .build();
        recordValidator.validate(record);
        assertTrue(recordValidator.hasErrors());
    }

    @Test
    public void recordValidateShouldHasMealValidateError1() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(null)
                .withDate(LocalDate.now().plusDays(1))
                .build();
        recordValidator.validate(record);
        assertTrue(recordValidator.hasErrors());
    }
}
