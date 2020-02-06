package ua.foodtracker.service.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.service.domain.Meal;
import ua.foodtracker.service.domain.Record;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecordMapperTest {
    private static final MealEntity MEAL_ENTITY = initMealEntity();
    private static final Meal MEAL = initMeal();
    public static final Integer INT_VALUE = 1;
    @Mock
    private RecordEntity recordEntity;
    @Mock
    private Record record;

    @Test
    public void mapRecordEntityToRecordReturnNotNull() {
        when(recordEntity.getId()).thenReturn(1);
        when(recordEntity.getDate()).thenReturn(new Date(System.currentTimeMillis()));
        when(recordEntity.getUserId()).thenReturn(INT_VALUE);
        when(recordEntity.getMealEntity()).thenReturn(MEAL_ENTITY);

        Record record = EntityMapper.mapEntityRecordToRecord(recordEntity);

        assertEquals(INT_VALUE, record.getId());
        assertEquals(INT_VALUE, record.getUserId());

        assertEquals(MEAL_ENTITY.getId(), record.getMeal().getId());
        assertEquals(MEAL_ENTITY.getCarbohydrate(), record.getMeal().getCarbohydrate());
        assertEquals(MEAL_ENTITY.getFat(), record.getMeal().getFat());
        assertEquals(MEAL_ENTITY.getProtein(), record.getMeal().getProtein());
        assertEquals(MEAL_ENTITY.getWater(), record.getMeal().getWater());
        assertEquals(MEAL_ENTITY.getWeight(), record.getMeal().getWeight());
        assertEquals(MEAL_ENTITY.getName(), record.getMeal().getName());
    }

    @Test
    public void mapRecordToRecordEntityReturnNotNull() {
        when(record.getId()).thenReturn(1);
        when(record.getDate()).thenReturn(LocalDate.now());
        when(record.getUserId()).thenReturn(INT_VALUE);
        when(record.getMeal()).thenReturn(MEAL);

        RecordEntity recordEntity = EntityMapper.mapRecordToEntityRecord(record);

        assertEquals(INT_VALUE, recordEntity.getId());
        assertEquals(INT_VALUE, recordEntity.getUserId());

        assertEquals(MEAL.getId(), recordEntity.getMealEntity().getId());
        assertEquals(MEAL.getCarbohydrate(), recordEntity.getMealEntity().getCarbohydrate());
        assertEquals(MEAL.getFat(), recordEntity.getMealEntity().getFat());
        assertEquals(MEAL.getProtein(), recordEntity.getMealEntity().getProtein());
        assertEquals(MEAL.getWater(), recordEntity.getMealEntity().getWater());
        assertEquals(MEAL.getWeight(), recordEntity.getMealEntity().getWeight());
        assertEquals(MEAL.getName(), recordEntity.getMealEntity().getName());
    }

    private static MealEntity initMealEntity() {
        return MealEntity.builder()
                .withCarbohydrates(1)
                .withFat(1)
                .withId(1)
                .withProtein(1)
                .withName("name")
                .withWater(1)
                .withUser(null)
                .withWeight(1)
                .build();
    }

    private static Meal initMeal() {
        return Meal.builder()
                .withCarbohydrates(1)
                .withFat(1)
                .withId(1)
                .withProtein(1)
                .withName("name")
                .withWater(1)
                .withUser(null)
                .withWeight(1)
                .build();
    }
}
