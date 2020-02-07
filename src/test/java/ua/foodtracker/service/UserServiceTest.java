package ua.foodtracker.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.domain.User;
import ua.foodtracker.service.impl.UserServiceImpl;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.UserValidator;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.foodtracker.service.utility.EntityMapper.mapUserToEntityUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final User RAW_USER = initUser();
    public static final User RAW_ADMIN = initAdmin();
    public static final UserEntity USER_ENTITY = mapUserToEntityUser(RAW_USER);
    public static final String PASS = "admin";
    public static final String INCORRECT_PASS = "not_admin";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private UserDao userDao;
    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMock() {
        reset(userDao);
    }

    @Test
    public void loginShouldReturnUser() {
        when(userDao.findByEmail(USER_ENTITY.getEmail())).thenReturn(Optional.of(USER_ENTITY));

        assertNotNull(userService.login(USER_ENTITY.getEmail(), PASS));

        verify(userDao).findByEmail(USER_ENTITY.getEmail());
    }

    @Test
    public void loginShouldThrowIncorrectDataException() {
        when(userDao.findByEmail(USER_ENTITY.getEmail())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        assertNotNull(userService.login(USER_ENTITY.getEmail(), PASS));

        verify(userDao).findByEmail(USER_ENTITY.getEmail());
    }

    @Test
    public void loginShouldThrowIncorrectDataExceptionCase2() {
        when(userDao.findByEmail(USER_ENTITY.getEmail())).thenReturn(Optional.of(USER_ENTITY));

        exception.expect(IncorrectDataException.class);
        userService.login(USER_ENTITY.getEmail(), INCORRECT_PASS);

        verify(userDao).findByEmail(USER_ENTITY.getEmail());
    }

    @Test
    public void registerShouldThrowIncorrectDataException() {
        UserEntity userEntity = EntityMapper.mapUserToEntityUser(RAW_USER);

        when(userDao.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(USER_ENTITY));
        doNothing().when(userValidator).validate(RAW_USER);

        exception.expect(IncorrectDataException.class);
        userService.register(RAW_USER);

        verify(userDao).findByEmail(userEntity.getEmail());
        verify(userValidator).validate(RAW_USER);
    }

    @Test
    public void registerShouldRegisterSuccessfully() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(RAW_USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(RAW_USER.getId());

        userService.register(RAW_USER);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator, times(2)).validate(RAW_USER);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldRegisterSuccessfully2() {

        doNothing().when(userValidator).validate(RAW_ADMIN);
        when(userDao.findByEmail(RAW_ADMIN.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(RAW_ADMIN.getId());

        userService.register(RAW_ADMIN);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(RAW_ADMIN).getEmail());
        verify(userValidator, times(2)).validate(RAW_ADMIN);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowIncorrectDataExceptionCase3() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(RAW_USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        userService.register(RAW_USER);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowValidationException() {
        UserEntity userEntity = EntityMapper.mapUserToEntityUser(RAW_USER);

        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        when(userValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        userService.register(RAW_USER);

        verify(userValidator).hasErrors();
        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator).validate(RAW_USER);
    }

    @Test
    public void modifyShouldntThrowException() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userValidator.hasErrors()).thenReturn(false);
        when(userDao.update(any())).thenReturn(true);

        userService.modify(RAW_USER);

        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
        verify(userDao).update(any());
    }

    @Test
    public void modifyShouldThrowValidationException() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        userService.modify(RAW_USER);

        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userValidator.hasErrors()).thenReturn(false);
        when(userDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        userService.modify(RAW_USER);

        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(userDao.findById(RAW_USER.getId())).thenReturn(Optional.of(USER_ENTITY));

        userService.findById(RAW_USER.getId().toString());

        verify(userDao).findById(RAW_USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(userDao.findById(RAW_USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        userService.findById(null);

        verify(userDao).findById(RAW_USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(userDao.findById(RAW_USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        userService.findById("ass");

        verify(userDao).findById(RAW_USER.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(userDao.deleteById(RAW_USER.getId())).thenReturn(true);

        userService.delete(RAW_USER.getId().toString());

        verify(userDao).deleteById(RAW_USER.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(userDao.deleteById(RAW_USER.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        userService.delete(RAW_USER.getId().toString());

        verify(userDao).deleteById(RAW_USER.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(userDao.deleteById(RAW_USER.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        userService.delete(null);

        verify(userDao).deleteById(RAW_USER.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(userDao.deleteById(RAW_USER.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        userService.delete("ass");

        verify(userDao).deleteById(RAW_USER.getId());
    }

    @Test
    public void setLocaleTest() {
        Locale current = Locale.getDefault();
        userService.setLocale(current);

        verify(userValidator).setLocale(current);
    }

    private static User initUser() {
        return User.builder()
                .withId(1)
                .withGender(Gender.MALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.USER)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusDays(80))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .build();
    }

    private static User initAdmin() {
        return User.builder()
                .withId(1)
                .withGender(Gender.FEMALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.ADMIN)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusDays(80))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .build();
    }
}
