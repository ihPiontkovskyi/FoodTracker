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
import ua.foodtracker.entity.User;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.entity.RawUser;
import ua.foodtracker.service.impl.UserServiceImpl;
import ua.foodtracker.validator.impl.UserValidator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.foodtracker.service.utility.EntityMapper.mapRawUserToEntityUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final RawUser RAW_USER = initUser();
    public static final RawUser RAW_ADMIN = initAdmin();
    public static final User USER = mapRawUserToEntityUser(RAW_USER);
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
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));

        assertEquals(USER, userService.login(USER.getEmail(), PASS));

        verify(userDao).findByEmail(USER.getEmail());
    }

    @Test
    public void loginShouldThrowIncorrectDataException() {
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        assertEquals(USER, userService.login(USER.getEmail(), PASS));

        verify(userDao).findByEmail(USER.getEmail());
    }

    @Test
    public void loginShouldThrowIncorrectDataExceptionCase2() {
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));

        exception.expect(IncorrectDataException.class);
        userService.login(USER.getEmail(), INCORRECT_PASS);

        verify(userDao).findByEmail(USER.getEmail());
    }

    @Test
    public void registerShouldThrowIncorrectDataException() {
        User user = mapRawUserToEntityUser(RAW_USER);

        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(USER));
        doNothing().when(userValidator).validate(RAW_USER);

        exception.expect(IncorrectDataException.class);
        userService.register(RAW_USER);

        verify(userDao).findByEmail(user.getEmail());
        verify(userValidator).validate(RAW_USER);
    }

    @Test
    public void registerShouldRegisterSuccessfully() {

        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(RAW_USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(RAW_USER.getId());

        userService.register(RAW_USER);

        verify(userDao).findByEmail(mapRawUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldRegisterSuccessfully2() {

        doNothing().when(userValidator).validate(RAW_ADMIN);
        when(userDao.findByEmail(RAW_ADMIN.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(RAW_ADMIN.getId());

        userService.register(RAW_ADMIN);

        verify(userDao).findByEmail(mapRawUserToEntityUser(RAW_ADMIN).getEmail());
        verify(userValidator).validate(RAW_ADMIN);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowIncorrectDataExceptionCase2() {
        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(RAW_USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(null);

        exception.expect(IncorrectDataException.class);
        userService.register(RAW_USER);

        verify(userDao).findByEmail(mapRawUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator).validate(RAW_USER);
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

        verify(userDao).findByEmail(mapRawUserToEntityUser(RAW_USER).getEmail());
        verify(userValidator).validate(RAW_USER);
        verify(userValidator).hasErrors();
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowValidationException() {
        User user = mapRawUserToEntityUser(RAW_USER);

        doNothing().when(userValidator).validate(RAW_USER);
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        userService.register(RAW_USER);

        verify(userValidator).hasErrors();
        verify(userDao).findByEmail(mapRawUserToEntityUser(RAW_USER).getEmail());
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
        when(userDao.findById(RAW_USER.getId())).thenReturn(Optional.of(USER));

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
    public void getPageShouldReturnList() {
        when(userDao.findAll(any())).thenReturn(Collections.emptyList());

        assertNotNull(userService.getPage(any()));
        assertEquals(userService.getPage(any()), Collections.EMPTY_LIST);

        verify(userDao, times(2)).findAll(any());
    }

    @Test
    public void getPageCountShouldReturn7() {
        when(userDao.count()).thenReturn(21L);

        assertEquals(7, userService.getPageCount());

        verify(userDao).count();
    }

    @Test
    public void getPageCountShouldReturn7Case2() {
        when(userDao.count()).thenReturn(20L);

        assertEquals(7, userService.getPageCount());

        verify(userDao).count();
    }

    @Test
    public void setLocaleTest() {
        Locale current = Locale.getDefault();
        userService.setLocale(current);

        verify(userValidator).setLocale(current);
    }

    private static RawUser initUser() {
        return RawUser.builder()
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

    private static RawUser initAdmin() {
        return RawUser.builder()
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
