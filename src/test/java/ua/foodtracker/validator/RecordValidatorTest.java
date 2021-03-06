package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.validator.impl.RecordValidatorImpl;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
        recordValidator = new RecordValidatorImpl();
    }

    @Test
    public void nullRecordValidateShouldHasError() {
        exception.expect(ValidationException.class);
        exception.expectMessage("object.is.null");
        recordValidator.validate(null);

    }

    @Test
    public void recordValidateShouldntHasErrors() {
        Record record = Record.builder()
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(LocalDate.now())
                .withWeight(10)
                .build();
        recordValidator.validate(record);
    }

    @Test
    public void recordValidateShouldHasDateValidateError() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(null)
                .withWeight(10)
                .build();
        exception.expect(ValidationException.class);
        exception.expectMessage("cant.set.date");
        recordValidator.validate(record);
    }

    @Test
    public void recordValidateShouldHasDateValidateError2() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withWeight(10)
                .withMeal(MEAL)
                .withDate(LocalDate.now().plusDays(1))
                .build();
        exception.expect(ValidationException.class);
        exception.expectMessage("cant.set.date");
        recordValidator.validate(record);
    }
}
