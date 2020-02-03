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
import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.service.impl.MealServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {
    public static final Meal MEAL = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .build();

    public static final Page page = new Page(1, 20L);

    @Mock
    private MealDao dao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private MealServiceImpl mealService;

    @After
    public void resetMock() {
        reset(dao);
    }

    @Test
    public void getPageCountShouldReturn2() {
        when(dao.count()).thenReturn(40L);
        assertEquals(2L, (long) mealService.getPageCount());
        verify(dao).count();
    }

    @Test
    public void getPageCountShouldReturn3() {
        when(dao.count()).thenReturn(41L);
        assertEquals(3L, (long) mealService.getPageCount());
        verify(dao).count();
    }

    @Test
    public void addShouldReturnTrue() {
        when(dao.save(MEAL)).thenReturn(MEAL.getId());
        assertTrue(mealService.add(MEAL));
        verify(dao).save(MEAL);
    }

    @Test
    public void deleteShouldReturnTrue() {
        when(dao.deleteById(MEAL.getId())).thenReturn(true);
        assertTrue(mealService.delete(MEAL));
        verify(dao).deleteById(MEAL.getId());
    }

    @Test
    public void addShouldReturnFalse() {
        when(dao.save(MEAL)).thenReturn(null);
        assertFalse(mealService.add(MEAL));
        verify(dao).save(MEAL);
    }

    @Test
    public void addShouldReturnFalse0() {
        when(dao.save(MEAL)).thenReturn(0);
        assertFalse(mealService.add(MEAL));
        verify(dao).save(MEAL);
    }

    @Test
    public void deleteShouldReturnFalse() {
        when(dao.deleteById(MEAL.getId())).thenReturn(false);
        assertFalse(mealService.delete(MEAL));
        verify(dao).deleteById(MEAL.getId());
    }

    @Test
    public void findByIdShouldReturnMeal() {
        when(dao.findById(MEAL.getId())).thenReturn(Optional.of(MEAL));
        assertTrue(mealService.findById(MEAL.getId()).isPresent());
        verify(dao).findById(MEAL.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalEmpty() {
        when(dao.findById(MEAL.getId())).thenReturn(Optional.empty());
        assertFalse(mealService.findById(MEAL.getId()).isPresent());
        verify(dao).findById(MEAL.getId());
    }

    @Test
    public void modifyShouldReturnTrue() {
        when(dao.update(MEAL)).thenReturn(true);
        assertTrue(mealService.modify(MEAL));
        verify(dao).update(MEAL);
    }

    @Test
    public void modifyShouldReturnFalse() {
        when(dao.update(MEAL)).thenReturn(false);
        assertFalse(mealService.modify(MEAL));
        verify(dao).update(MEAL);
    }

    @Test
    public void getPageShouldReturnNotEmptyList() {
        List<Meal> res = new ArrayList<>();
        res.add(MEAL);
        res.add(MEAL);
        when(dao.findAll(any())).thenReturn(res);
        assertFalse(mealService.getPage(page.getPageNumber(), any()).isEmpty());
        verify(dao).findAll(any());
    }
}
