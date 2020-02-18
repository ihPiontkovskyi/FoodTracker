package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Test;
import ua.foodtracker.domain.User;
import ua.foodtracker.validator.impl.UserValidatorImpl;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    private Validator<User> userValidator;

    @Before
    public void initValidator() {
        userValidator = new UserValidatorImpl();
    }

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
                .withBirthday(LocalDate.now().minusDays(7))
                .build();
        userValidator.validate(user);
        assertFalse(userValidator.hasErrors());
    }

    @Test
    public void userValidatorShouldntHasErrors1() {
        userValidator.validate(null);
        assertTrue(userValidator.hasErrors());
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("height"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("weight"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("first name"));
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
                .withBirthday(LocalDate.now())
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("last name"));
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
                .withBirthday(LocalDate.now().plusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("birthday"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateErrorByEmptyEmail() {
        User user = User.builder()
                .withId(1)
                .withEmail("")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateErrorByNullEmail() {
        User user = User.builder()
                .withId(1)
                .withEmail(null)
                .withHeight(10)
                .withPassword("")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasPassValidateErrorByEmptyPass() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword(null)
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasPassValidateErrorByNullPass() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword(null)
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasPassValidateErrorByLongPass() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("123456789123456789123456789123456789")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasPassValidateErrorBySmallPass() {
        User user = User.builder()
                .withId(1)
                .withEmail("email@email.email")
                .withHeight(10)
                .withPassword("12")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("password"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateErrorBySmallEmail() {
        User user = User.builder()
                .withId(1)
                .withEmail("a")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
    }

    @Test
    public void userValidatorShouldHasEmailValidateErrorByLongEmail() {
        User user = User.builder()
                .withId(1)
                .withEmail("12345678901123456789@123456789.1231231123456789")
                .withHeight(10)
                .withPassword("password")
                .withFirstName("Firtsname")
                .withLastName("Lastname")
                .withWeight(10)
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        assertTrue(userValidator.getMessages().containsKey("email"));
        assertEquals("Email length should be in the range from 3 to 32 ", userValidator.getErrorMessageByIssues());
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
                .withBirthday(LocalDate.now().minusDays(1))
                .build();
        userValidator.validate(user);
        int mapSize = userValidator.getMessages().size();
        userValidator.putIssue(null, "issue");
        assertEquals(mapSize, userValidator.getMessages().size());
        userValidator.putIssue("key", null);
        assertEquals(mapSize, userValidator.getMessages().size());
    }

    @Test
    public void localeTest() {
        Locale locale = Locale.getDefault();
        userValidator.setLocale(locale);
        assertEquals(locale, userValidator.getLocale());
    }
}
