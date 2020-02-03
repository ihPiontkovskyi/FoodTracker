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
import ua.foodtracker.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final User USER = User.builder()
            .withId(1)
            .withGender(Gender.MALE)
            .withLifestyle(Lifestyle.NOT_SELECTED)
            .withRole(Role.USER)
            .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
            .withWeight(90)
            .withHeight(190)
            .withLastName("last name")
            .withFirstName("first name")
            .withEmail("email")
            .build();

    @Mock
    private UserDao dao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMock() {
        reset(dao);
    }

    @Test
    public void getPageCountShouldReturn2() {
        when(dao.count()).thenReturn(40L);
        assertEquals(2L, (long) userService.getPageCount());
        verify(dao).count();
    }

    @Test
    public void getPageCountShouldReturn3() {
        when(dao.count()).thenReturn(41L);
        assertEquals(3L, (long) userService.getPageCount());
        verify(dao).count();
    }

    @Test
    public void addShouldReturnTrue() {
        when(dao.save(USER)).thenReturn(USER.getId());
        assertTrue(userService.register(USER));
        verify(dao).save(USER);
    }

    @Test
    public void deleteShouldReturnTrue() {
        when(dao.deleteById(USER.getId())).thenReturn(true);
        assertTrue(userService.delete(USER.getId()));
        verify(dao).deleteById(USER.getId());
    }

    @Test
    public void addShouldReturnFalse() {
        when(dao.save(USER)).thenReturn(null);
        assertFalse(userService.register(USER));
        verify(dao).save(USER);
    }

    @Test
    public void addShouldReturnFalse0() {
        when(dao.save(USER)).thenReturn(0);
        assertFalse(userService.register(USER));
        verify(dao).save(USER);
    }

    @Test
    public void deleteShouldReturnFalse() {
        when(dao.deleteById(USER.getId())).thenReturn(false);
        assertFalse(userService.delete(USER.getId()));
        verify(dao).deleteById(USER.getId());
    }

    @Test
    public void findByIdShouldReturnMeal() {
        when(dao.findById(USER.getId())).thenReturn(Optional.of(USER));
        assertTrue(userService.findById(USER.getId()).isPresent());
        verify(dao).findById(USER.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalEmpty() {
        when(dao.findById(USER.getId())).thenReturn(Optional.empty());
        assertFalse(userService.findById(USER.getId()).isPresent());
        verify(dao).findById(USER.getId());
    }

    @Test
    public void modifyShouldReturnTrue() {
        when(dao.update(USER)).thenReturn(true);
        assertTrue(userService.modify(USER));
        verify(dao).update(USER);
    }

    @Test
    public void modifyShouldReturnFalse() {
        when(dao.update(USER)).thenReturn(false);
        assertFalse(userService.modify(USER));
        verify(dao).update(USER);
    }

    @Test
    public void getPageShouldReturnNotEmptyList() {
        List<User> res = new ArrayList<>();
        res.add(USER);
        res.add(USER);
        when(dao.findAll(any())).thenReturn(res);
        assertFalse(userService.getPage(1).isEmpty());
        verify(dao).findAll(any());
    }

    @Test
    public void loginShouldReturnNull() {
        when(dao.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));
        assertNull(userService.login(USER.getEmail(), USER.getPassword()));
        verify(dao).findByEmail(USER.getEmail());
    }

    @Test
    public void loginShouldReturnNull2() {
        when(dao.findByEmail(USER.getEmail())).thenReturn(Optional.empty());
        assertNull(userService.login(USER.getEmail(), USER.getPassword()));
        verify(dao).findByEmail(USER.getEmail());
    }

    @Test
    public void loginShouldReturnUser() {
        when(dao.findByEmail(USER.getEmail())).thenReturn(Optional.ofNullable(USER));
        assertNotNull(userService.login(USER.getEmail(), "admin"));
        verify(dao).findByEmail(USER.getEmail());
    }
}
