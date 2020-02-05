package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Test;
import ua.foodtracker.service.entity.RawMeal;
import ua.foodtracker.service.entity.RawRecord;
import ua.foodtracker.service.entity.RawUser;
import ua.foodtracker.validator.impl.RecordValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordValidatorTest {
    private static final RawUser USER = RawUser.builder()
            .withId(1)
            .withEmail("email@email.email")
            .withHeight(10)
            .withPassword("password")
            .withFirstName("Firtsname")
            .withLastName("Lastname")
            .withWeight(10)
            .withBirthday(LocalDate.now())
            .build();
    private static final RawMeal MEAL = RawMeal.builder()
            .withId(1)
            .withWeight(10)
            .withUser(USER)
            .withWater(10)
            .withProtein(10)
            .withName("name")
            .withFat(10)
            .withCarbohydrates(10)
            .build();

    private Validator<RawRecord> recordValidator;

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
        RawRecord record = RawRecord.builder()
                .withUserId(1)
                .withMeal(MEAL)
                .withDate(LocalDate.now())
                .build();
        recordValidator.validate(record);
        assertFalse(recordValidator.hasErrors());
    }

    @Test
    public void recordValidateShouldHasDateValidateError() {
        RawRecord record = RawRecord.builder()
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
        RawRecord record = RawRecord.builder()
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
        RawRecord record = RawRecord.builder()
                .withUserId(1)
                .withUserId(1)
                .withMeal(null)
                .withDate(LocalDate.now().plusDays(1))
                .build();
        recordValidator.validate(record);
        assertTrue(recordValidator.hasErrors());
    }
}
