package ua.foodtracker.validator;

import org.junit.Test;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.User;
import ua.foodtracker.validator.impl.RecordValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

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
            .withBirthday(new Date(System.currentTimeMillis()))
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

    @Test
    public void nullRecordValidateShouldHasError() {
        Validator validator = new RecordValidator(null, Locale.getDefault());
        assertTrue(validator.hasErrors());
    }

    @Test
    public void recordValidateShouldntHasErrors() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(new Date(System.currentTimeMillis()))
                .build();
        assertFalse(new RecordValidator(record, Locale.getDefault()).hasErrors());
    }

    @Test
    public void recordValidateShouldHasDateValidateError() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(null)
                .build();
        assertTrue(new RecordValidator(record, Locale.getDefault()).hasErrors());
    }

    @Test
    public void recordValidateShouldHasDateValidateError2() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .build();
        assertTrue(new RecordValidator(record, Locale.getDefault()).hasErrors());
    }

    @Test
    public void recordValidateShouldHasMealValidateError() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(Meal.builder().build())
                .withDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .build();
        assertTrue(new RecordValidator(record, Locale.getDefault()).hasErrors());
    }

    @Test
    public void recordValidateShouldHasMealValidateError1() {
        Record record = Record.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(null)
                .withDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .build();
        assertTrue(new RecordValidator(record, Locale.getDefault()).hasErrors());
    }
}
