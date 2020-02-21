package ua.foodtracker.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MealTest {
    @Parameter
    public Meal mealEntity;
    @Parameter(1)
    public Integer expected;

    @Parameters(name = "id={0}, Lifestyle={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {Meal.builder()
                        .withCarbohydrates(60)
                        .withFat(10)
                        .withProtein(10)
                        .build(), 60 * 4 + 9 * 10 + 10 * 4},
                {Meal.builder()
                        .withCarbohydrates(10)
                        .withFat(0)
                        .withProtein(14)
                        .build(), 10 * 4 + 14 * 4},
                {Meal.builder()
                        .withCarbohydrates(10)
                        .withFat(10)
                        .withProtein(10)
                        .build(), 10 * 4 + 9 * 10 + 10 * 4}
        });
    }

    @Test
    public void lifestyleGetByIdTest() {
        Assert.assertEquals(expected, mealEntity.calculateEnergy());
    }
}
