package ua.foodtracker.dao.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GenderTest {
    @Parameterized.Parameter
    public Integer id;
    @Parameterized.Parameter(1)
    public Gender expected;

    @Parameterized.Parameters(name = "id={0}, Gender={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {1, Gender.MALE},
                {2, Gender.FEMALE},
                {3, Gender.OTHER},
                {4, Gender.NOT_SELECTED},
                {-1, Gender.NOT_SELECTED},
                {null, Gender.NOT_SELECTED},
        });
    }

    @Test
    public void genderGetByIdTest() {
        Assert.assertEquals(expected, Gender.getGenderById(id));
    }
}
