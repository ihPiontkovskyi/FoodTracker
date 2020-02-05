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
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Role;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.entity.RawMeal;
import ua.foodtracker.service.entity.RawUser;
import ua.foodtracker.service.impl.MealServiceImpl;
import ua.foodtracker.service.utility.EntityMapper;
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

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {
    private RawUser RAW_USER = RawUser.builder()
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
    private RawMeal RAW_MEAL = RawMeal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .withUser(RAW_USER)
            .build();
    public Meal MEAL = EntityMapper.mapRawMealToEntityMeal(RAW_MEAL);
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
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.update(any())).thenReturn(true);

        mealService.modify(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
        verify(mealDao).update(any());
    }

    @Test
    public void modifyShouldThrowValidationException() {
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        mealService.modify(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.modify(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(mealDao.findById(RAW_MEAL.getId())).thenReturn(Optional.of(MEAL));

        mealService.findById(RAW_MEAL.getId().toString());

        verify(mealDao).findById(RAW_MEAL.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(mealDao.findById(RAW_MEAL.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById(null);

        verify(mealDao).findById(RAW_MEAL.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(mealDao.findById(RAW_MEAL.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById("ass");

        verify(mealDao).findById(RAW_MEAL.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(mealDao.deleteById(RAW_MEAL.getId())).thenReturn(true);

        mealService.delete(RAW_MEAL.getId().toString());

        verify(mealDao).deleteById(RAW_MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(mealDao.deleteById(RAW_MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(RAW_MEAL.getId().toString());

        verify(mealDao).deleteById(RAW_MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(mealDao.deleteById(RAW_MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(null);

        verify(mealDao).deleteById(RAW_MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(mealDao.deleteById(RAW_MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete("ass");

        verify(mealDao).deleteById(RAW_MEAL.getId());
    }

    @Test
    public void findAllByPageShouldReturnList() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());

        assertNotNull(mealService.findAllByPage(any(), RAW_MEAL.getUser().getId()));
        assertEquals(Collections.EMPTY_LIST, mealService.findAllByPage(any(), RAW_MEAL.getUser().getId()));

        verify(mealDao, times(2)).findAll(any());
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
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        mealService.add(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
    }

    @Test
    public void addShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        mealService.add(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
        verify(mealDao).save(any());
    }

    @Test
    public void addShouldThrowIncorrectDataExceptionCase2() {
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.save(any())).thenReturn(null);

        exception.expect(IncorrectDataException.class);
        mealService.add(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
        verify(mealDao).save(any());
    }

    @Test
    public void addShouldntThrowException() {
        doNothing().when(mealValidator).validate(RAW_MEAL);
        when(mealValidator.hasErrors()).thenReturn(false);
        when(mealDao.save(any())).thenReturn(RAW_MEAL.getId());

        mealService.add(RAW_MEAL);

        verify(mealValidator).validate(RAW_MEAL);
        verify(mealValidator).hasErrors();
        verify(mealDao).save(any());
    }

    private void initFileds() {

    }
}
