package ua.foodtracker.dao.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LifestyleTest {
    @Parameterized.Parameter
    public Integer id;
    @Parameterized.Parameter(1)
    public Lifestyle expected;

    @Parameterized.Parameters(name = "id={0}, Lifestyle={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {1, Lifestyle.SEDENTARY},
                {2, Lifestyle.LIGHTLY_ACTIVE},
                {3, Lifestyle.ACTIVE},
                {4, Lifestyle.VERY_ACTIVE},
                {-1, Lifestyle.NOT_SELECTED},
                {null, Lifestyle.NOT_SELECTED},
                {5, Lifestyle.NOT_SELECTED},
        });
    }

    @Test
    public void lifestyleGetByIdTest() {
        Assert.assertEquals(expected, Lifestyle.getLifestyleById(id));
    }
}