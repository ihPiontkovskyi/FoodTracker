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
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.impl.UserServiceImpl;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.UserValidatorImpl;

import java.time.LocalDate;
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

    public static final User USER = initUser();
    public static final User ADMIN = initAdmin();
    public static final UserEntity USER_ENTITY = mapUserToEntityUser(USER);
    public static final String PASS = "admin";
    public static final String INCORRECT_PASS = "not_admin";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private UserDao userDao;
    @Mock
    private UserValidatorImpl userValidator;

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
        UserEntity userEntity = EntityMapper.mapUserToEntityUser(USER);

        when(userDao.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(USER_ENTITY));
        doNothing().when(userValidator).validate(USER);

        exception.expect(IncorrectDataException.class);
        userService.register(USER);

        verify(userDao).findByEmail(userEntity.getEmail());
        verify(userValidator).validate(USER);
    }

    @Test
    public void registerShouldRegisterSuccessfully() {
        doNothing().when(userValidator).validate(USER);
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(USER.getId());

        userService.register(USER);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(USER).getEmail());
        verify(userValidator, times(2)).validate(USER);
        verify(userDao).save(any());
    }

    @Test
    public void registerShouldRegisterSuccessfully2() {

        doNothing().when(userValidator).validate(ADMIN);
        when(userDao.findByEmail(ADMIN.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(ADMIN.getId());

        userService.register(ADMIN);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(ADMIN).getEmail());
        verify(userValidator, times(2)).validate(ADMIN);

        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowIncorrectDataExceptionCase3() {
        doNothing().when(userValidator).validate(USER);
        when(userDao.findByEmail(USER.getEmail())).thenReturn(Optional.empty());
        when(userDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        userService.register(USER);

        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(USER).getEmail());
        verify(userValidator).validate(USER);

        verify(userDao).save(any());
    }

    @Test
    public void registerShouldThrowIncorrectDataExceptionCase() {
        UserEntity userEntity = EntityMapper.mapUserToEntityUser(USER);

        doNothing().when(userValidator).validate(USER);
        when(userDao.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());


        exception.expect(IncorrectDataException.class);
        userService.register(USER);


        verify(userDao).findByEmail(EntityMapper.mapUserToEntityUser(USER).getEmail());
        verify(userValidator).validate(USER);
    }

    @Test
    public void modifyShouldntThrowException() {
        doNothing().when(userValidator).validate(USER);

        when(userDao.update(any())).thenReturn(true);

        userService.modify(USER);

        verify(userValidator).validate(USER);

        verify(userDao).update(any());
    }

    @Test
    public void modifyShouldThrowIncorrectDataExceptionCase() {
        doNothing().when(userValidator).validate(USER);


        exception.expect(IncorrectDataException.class);
        userService.modify(USER);

        verify(userValidator).validate(USER);

    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(userValidator).validate(USER);

        when(userDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        userService.modify(USER);

        verify(userValidator).validate(USER);

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
