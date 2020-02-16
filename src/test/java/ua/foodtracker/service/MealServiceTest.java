package ua.foodtracker.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.entity.GenderEntity;
import ua.foodtracker.entity.LifestyleEntity;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RoleEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.impl.MealServiceImpl;
import ua.foodtracker.validator.impl.MealValidator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.foodtracker.service.utility.EntityMapper.mapMealToEntityMeal;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {
    private User user = User.builder()
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
    private Meal meal = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .withUser(user)
            .build();
    public MealEntity mealEntity = mapMealToEntityMeal(meal);
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private MealDao mealDao;
    @Mock
    private MealValidator mealValidator;

    @InjectMocks
    private MealServiceImpl mealService;

    @After
    public void resetMock() {
        reset(mealDao);
    }

    @Test
    public void modifyShouldntThrowException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.update(any())).thenReturn(true);

        mealService.modify(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
        verify(mealDao).update(any());
    }

    @Test
    public void modifyShouldThrowValidationException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        mealService.modify(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.modify(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(mealDao.findById(meal.getId())).thenReturn(Optional.of(mealEntity));

        mealService.findById(meal.getId().toString());

        verify(mealDao).findById(meal.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(mealDao.findById(meal.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById(null);

        verify(mealDao).findById(meal.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(mealDao.findById(meal.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById("ass");

        verify(mealDao).findById(meal.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(mealDao.deleteById(meal.getId())).thenReturn(true);

        mealService.delete(meal.getId().toString());

        verify(mealDao).deleteById(meal.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(mealDao.deleteById(meal.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(meal.getId().toString());

        verify(mealDao).deleteById(meal.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(mealDao.deleteById(meal.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(null);

        verify(mealDao).deleteById(meal.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(mealDao.deleteById(meal.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete("ass");

        verify(mealDao).deleteById(meal.getId());
    }

    @Test
    public void findAllByPageShouldReturnList() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());
        when(mealDao.count()).thenReturn(5000L);

        assertNotNull(mealService.findAllByPage("1"));
        assertEquals(Collections.emptyList(), mealService.findAllByPage("2"));

        verify(mealDao, times(2)).findAll(any());
        verify(mealDao,times(2)).count();
    }

    @Test
    public void findAllByPageShouldReturnListCase2() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());
        when(mealDao.count()).thenReturn(5000L);

        exception.expect(IncorrectDataException.class);
        assertNotNull(mealService.findAllByPage("aaa"));
        assertEquals(Collections.emptyList(), mealService.findAllByPage("aaa"));

        verify(mealDao, times(2)).findAll(any());
        verify(mealDao).count();
    }

    @Test
    public void findAllByPageShouldReturnListCase3() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());

        assertNotNull(mealService.findAllByPage("-1"));
        assertEquals(Collections.emptyList(), mealService.findAllByPage("-1"));

        verify(mealDao, times(2)).findAll(any());
    }

    @Test
    public void findAllByPageShouldReturnListCase4() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());
        when(mealDao.count()).thenReturn(5000L);

        assertNotNull(mealService.findAllByPage("5000000"));
        assertEquals(Collections.emptyList(), mealService.findAllByPage("50000000"));

        verify(mealDao, times(2)).findAll(any());
        verify(mealDao, times(2)).count();
    }

    @Test
    public void pageCountShouldReturn7() {
        when(mealDao.count()).thenReturn(21L);

        assertEquals(7, mealService.pageCount());

        verify(mealDao).count();
    }

    @Test
    public void pageCountShouldReturn7Case2() {
        when(mealDao.count()).thenReturn(20L);

        assertEquals(7, mealService.pageCount());

        verify(mealDao).count();
    }

    @Test
    public void setLocaleTest() {
        Locale current = Locale.getDefault();
        mealService.setLocale(current);

        verify(mealValidator).setLocale(current);
    }

    @Test
    public void addShouldThrowValidationException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        mealService.add(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void addShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        mealService.add(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
        verify(mealDao).save(any());
    }

    @Test
    public void addShouldntThrowException() {
        doNothing().when(mealValidator).validate(meal);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.save(any())).thenReturn(meal.getId());

        mealService.add(meal);

        verify(mealValidator).validate(meal);
        verify(mealValidator).hasErrors();
        verify(mealDao).save(any());
    }
}
