package ua.foodtracker.validator;

import org.junit.Test;
import ua.foodtracker.entity.User;
import ua.foodtracker.validator.impl.UserValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {
    @Test
    public void userValidatorShouldntHasErrors() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(Date.valueOf(LocalDate.now().minusDays(7)))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertFalse(validator.hasErrors());
    }

    @Test
    public void userValidatorShouldntHasErrors1() {
        Validator validator = new UserValidator(null, Locale.getDefault());
        assertTrue(validator.hasErrors());
    }

    @Test
    public void userValidatorShouldHasEmailValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("eil@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateError1() {
        User user = User.builder()
                .withId(1)
                .withEmail("")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateError2() {
        User user = User.builder()
                .withId(1)
                .withEmail("emaillllllllllllllllllllllaaaaaaaaaaaaalllll@mail.mail")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasHeightValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(-1)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("height"));
    }

    @Test
    public void userValidatorShouldHasWeightValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(-10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("weight"));
    }

    @Test
    public void userValidatorShouldHasPasswordValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password--")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasPasswordValidateError1() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasPasswordValidateError2() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("as")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasFirstNameValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname11")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("first name"));
    }

    @Test
    public void userValidatorShouldHasLastNameValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname11")
                .withWeight(10)
                .withBirthday(new Date(System.currentTimeMillis()))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("last name"));
    }

    @Test
    public void userValidatorShouldHasBirthDayValidateError() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(Date.valueOf(LocalDate.now().plusDays(1)))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        assertTrue(validator.getMessages().containsKey("birthday"));
    }

    @Test
    public void putIssueNullTest() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(Date.valueOf(LocalDate.now().minusDays(1)))
                .build();
        Validator validator = new UserValidator(user, Locale.getDefault());
        int mapSize = validator.getMessages().size();
        validator.putIssue(null, "issue");
        assertEquals(mapSize, validator.getMessages().size());
        validator.putIssue("key", null);
        assertEquals(mapSize, validator.getMessages().size());
    }
}
