package ua.foodtracker.validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.foodtracker.domain.User;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.validator.impl.UserValidatorImpl;

import java.time.LocalDate;

public class UserValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    }

    @Test
    public void userValidatorShouldntHasErrors1() {
        exception.expect(ValidationException.class);
        exception.expectMessage("object.is.null");
        userValidator.validate(null);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.should.match.email.pattern");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.cant.be.empty");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.length.should.be.in.range(3,32)");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("value.cant.be.less.then.0");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.should.contains.letter.only");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("name.should.contains.letter.only");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("cant.set.date");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.cant.be.empty");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.cant.be.empty");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.length.should.be.in.range(3,32)");
        userValidator.validate(user);
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
        exception.expect(ValidationException.class);
        exception.expectMessage("email.length.should.be.in.range(3,32)");
        userValidator.validate(user);
    }
}
