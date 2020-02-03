package ua.foodtracker.entity;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Test
    public void testBuildUserWithNullFieldShouldReturnDefaultUser() {
        assertNotNull(User.builder().build());
        assertNotNull(User.builder().build().getUserGoal());
    }

    @Test
    public void testBuildUserWithNullFieldShouldReturnDefaultUser1() {
        assertNotNull(User.builder().withGender(Gender.OTHER).build());
        assertNotNull(User.builder().withGender(Gender.OTHER).build());
    }
    @Test
    public void testBuildUserWithNullFieldShouldReturnDefaultUser2() {
        assertNotNull(User.builder().withGender(Gender.FEMALE).build());
        assertNotNull(User.builder().withGender(Gender.FEMALE).build());
    }
}
