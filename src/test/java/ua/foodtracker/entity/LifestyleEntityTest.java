package ua.foodtracker.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LifestyleEntityTest {
    @Parameterized.Parameter
    public Integer id;
    @Parameterized.Parameter(1)
    public LifestyleEntity expected;

    @Parameterized.Parameters(name = "id={0}, Lifestyle={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {1, LifestyleEntity.SEDENTARY},
                {2, LifestyleEntity.LIGHTLY_ACTIVE},
                {3, LifestyleEntity.ACTIVE},
                {4, LifestyleEntity.VERY_ACTIVE},
                {-1, LifestyleEntity.NOT_SELECTED},
                {null, LifestyleEntity.NOT_SELECTED},
                {5, LifestyleEntity.NOT_SELECTED},
        });
    }

    @Test
    public void lifestyleGetByIdTest() {
        Assert.assertEquals(expected, LifestyleEntity.getLifestyleById(id));
    }
}