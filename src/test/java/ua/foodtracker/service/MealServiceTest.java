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
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.impl.MealServiceImpl;
import ua.foodtracker.validator.impl.MealValidatorImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.foodtracker.service.utility.EntityMapper.mapMealToEntityMeal;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {
    private static final User USER = User.builder()
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
    private static final Meal MEAL = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .withUser(USER)
            .build();

    private static final MealEntity MEAL_ENTITY = mapMealToEntityMeal(MEAL);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private MealDao mealDao;
    @Mock
    private MealValidatorImpl mealValidator;

    @InjectMocks
    private MealServiceImpl mealService;

    @After
    public void resetMock() {
        reset(mealDao);
    }

    @Test
    public void modifyShouldntThrowException() {
        doNothing().when(mealValidator).validate(MEAL);
        when(mealDao.update(any())).thenReturn(true);

        mealService.modify(MEAL);

        verify(mealValidator).validate(MEAL);
        verify(mealDao).update(any());
    }

    @Test
    public void modifyShouldThrowIncorrectDataExceptionCase() {
        doNothing().when(mealValidator).validate(MEAL);

        exception.expect(IncorrectDataException.class);
        mealService.modify(MEAL);

        verify(mealValidator).validate(MEAL);
    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(MEAL);
        when(mealDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.modify(MEAL);

        verify(mealValidator).validate(MEAL);
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(mealDao.findById(MEAL.getId())).thenReturn(Optional.of(MEAL_ENTITY));

        mealService.findById(MEAL.getId().toString());

        verify(mealDao).findById(MEAL.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(mealDao.findById(MEAL.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById(null);

        verify(mealDao).findById(MEAL.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(mealDao.findById(MEAL.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        mealService.findById("ass");

        verify(mealDao).findById(MEAL.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(mealDao.deleteById(MEAL.getId())).thenReturn(true);

        mealService.delete(MEAL.getId().toString());

        verify(mealDao).deleteById(MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(mealDao.deleteById(MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(MEAL.getId().toString());

        verify(mealDao).deleteById(MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(mealDao.deleteById(MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete(null);

        verify(mealDao).deleteById(MEAL.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(mealDao.deleteById(MEAL.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        mealService.delete("ass");

        verify(mealDao).deleteById(MEAL.getId());
    }

    @Test
    public void findAllByPageShouldReturnList() {
        when(mealDao.findAll(any())).thenReturn(Collections.emptyList());
        when(mealDao.count()).thenReturn(5000L);

        assertNotNull(mealService.findAllByPage("1"));
        assertEquals(Collections.emptyList(), mealService.findAllByPage("2"));

        verify(mealDao, times(2)).findAll(any());
        verify(mealDao, times(2)).count();
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
    public void pageCountShouldReturn3() {
        when(mealDao.count()).thenReturn(21L);

        assertEquals(3, mealService.pageCount());

        verify(mealDao).count();
    }

    @Test
    public void pageCountShouldReturn2Case2() {
        when(mealDao.count()).thenReturn(20L);

        assertEquals(2, mealService.pageCount());

        verify(mealDao).count();
    }

    @Test
    public void addShouldThrowIncorrectDataExceptionCase() {
        doNothing().when(mealValidator).validate(MEAL);

        exception.expect(IncorrectDataException.class);
        mealService.add(MEAL);

        verify(mealValidator).validate(MEAL);
    }

    @Test
    public void addShouldThrowIncorrectDataException() {
        doNothing().when(mealValidator).validate(MEAL);
        when(mealDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        mealService.add(MEAL);

        verify(mealValidator).validate(MEAL);
        verify(mealDao).save(any());
    }

    @Test
    public void addShouldntThrowException() {
        doNothing().when(mealValidator).validate(MEAL);
        when(mealDao.save(any())).thenReturn(MEAL.getId());

        mealService.add(MEAL);

        verify(mealValidator).validate(MEAL);
        verify(mealDao).save(any());
    }

    @Test
    public void findByNameStartWithShouldReturnEmptyList() {
        when(mealDao.findAllByNameStartWith("term")).thenReturn(Collections.emptyList());

        assertThat(mealService.findAllByNameStartWith("term"), equalTo(Collections.emptyList()));

        verify(mealDao).findAllByNameStartWith("term");
    }

    @Test
    public void findByNameStartWithShouldReturnList() {
        when(mealDao.findAllByNameStartWith("term")).thenReturn(Collections.singletonList(MEAL_ENTITY));

        assertThat(mealService.findAllByNameStartWith("term"), hasItem(notNullValue()));

        verify(mealDao).findAllByNameStartWith("term");
    }
}
