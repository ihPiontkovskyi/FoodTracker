package ua.foodtracker.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GenderEntityTest {
    @Parameterized.Parameter
    public Integer id;
    @Parameterized.Parameter(1)
    public GenderEntity expected;

    @Parameterized.Parameters(name = "id={0}, Gender={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {1, GenderEntity.MALE},
                {2, GenderEntity.FEMALE},
                {3, GenderEntity.OTHER},
                {4, GenderEntity.NOT_SELECTED},
                {-1, GenderEntity.NOT_SELECTED},
                {null, GenderEntity.NOT_SELECTED},
        });
    }

    @Test
    public void genderGetByIdTest() {
      //  Assert.assertEquals(expected, GenderEntity.getGenderById(id));
    }
}
