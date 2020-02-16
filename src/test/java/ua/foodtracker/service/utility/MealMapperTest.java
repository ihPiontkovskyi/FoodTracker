package ua.foodtracker.service.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.domain.Meal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealMapperTest {
    public static final Integer INT_VALUE = 1;
    @Mock
    private MealEntity mealEntity;
    @Mock
    private Meal meal;

    @Test
    public void mapMealToMealEntityShouldReturnNotNullMeal() {
        when(meal.getId()).thenReturn(INT_VALUE);
        when(meal.getCarbohydrate()).thenReturn(INT_VALUE);
        when(meal.getFat()).thenReturn(INT_VALUE);
        when(meal.getProtein()).thenReturn(INT_VALUE);
        when(meal.getWater()).thenReturn(INT_VALUE);
        when(meal.getName()).thenReturn("name");
        when(meal.getWeight()).thenReturn(INT_VALUE);
        when(meal.getUser()).thenReturn(null);

        MealEntity entity = EntityMapper.mapMealToEntityMeal(meal);

        assertEquals(INT_VALUE, entity.getId());
        assertEquals(INT_VALUE, entity.getCarbohydrate());
        assertEquals(INT_VALUE, entity.getFat());
        assertEquals(INT_VALUE, entity.getProtein());
        assertEquals(INT_VALUE, entity.getWater());
        assertEquals(INT_VALUE, entity.getWeight());
        assertEquals("name", entity.getName());
        assertNull(entity.getUserEntity());
    }

    @Test
    public void mapMealEntityToMealShouldReturnNotNullMeal() {
        when(mealEntity.getId()).thenReturn(INT_VALUE);
        when(mealEntity.getCarbohydrate()).thenReturn(INT_VALUE);
        when(mealEntity.getFat()).thenReturn(INT_VALUE);
        when(mealEntity.getProtein()).thenReturn(INT_VALUE);
        when(mealEntity.getWater()).thenReturn(INT_VALUE);
        when(mealEntity.getName()).thenReturn("name");
        when(mealEntity.getWeight()).thenReturn(INT_VALUE);
        when(mealEntity.getUserEntity()).thenReturn(null);

        Meal meal = EntityMapper.mapEntityMealToMeal(mealEntity);

        assertEquals(INT_VALUE, meal.getId());
        assertEquals(INT_VALUE, meal.getCarbohydrate());
        assertEquals(INT_VALUE, meal.getFat());
        assertEquals(INT_VALUE, meal.getProtein());
        assertEquals(INT_VALUE, meal.getWater());
        assertEquals(INT_VALUE, meal.getWeight());
        assertEquals("name", meal.getName());
        assertNull(meal.getUser());
    }
}
