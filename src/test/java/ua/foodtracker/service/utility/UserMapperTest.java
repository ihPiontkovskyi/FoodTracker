package ua.foodtracker.service.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;
import ua.foodtracker.service.domain.User;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    private static final UserGoalEntity goal = initGoal();

    @Mock
    private UserEntity userEntity;
    @Mock
    private User user;

    @Test
    public void mapUserToUserEntityShouldReturnNotNullUserCaseMale() {
        when(user.getId()).thenReturn(1);
        when(user.getHeight()).thenReturn(1);
        when(user.getWeight()).thenReturn(1);
        when(user.getEmail()).thenReturn("email");
        when(user.getPassword()).thenReturn("password");
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getLifestyle()).thenReturn(Lifestyle.ACTIVE);
        when(user.getBirthday()).thenReturn(LocalDate.now());
        when(user.getGender()).thenReturn(Gender.MALE);
        when(user.getFirstName()).thenReturn("name");
        when(user.getLastName()).thenReturn("lastname");

        UserEntity entity = EntityMapper.mapUserToEntityUser(user);

        assertEquals(1, (int) entity.getId());
        assertEquals(1, (int) entity.getWeight());
        assertEquals(1, (int) entity.getHeight());
        assertEquals("email", entity.getEmail());
        assertEquals("password", entity.getPassword());
        assertEquals(Role.USER, entity.getRole());
        assertEquals(Lifestyle.ACTIVE, entity.getLifestyle());
        assertEquals(LocalDate.now(), entity.getBirthday().toLocalDate());
        assertEquals(Gender.MALE, entity.getGender());
        assertEquals("name", entity.getFirstName());
        assertEquals("lastname", entity.getLastName());
        assertNotNull(entity.getUserGoalEntity());
    }

    @Test
    public void mapUserToUserEntityShouldReturnNotNullUserCaseOther() {
        when(user.getId()).thenReturn(1);
        when(user.getHeight()).thenReturn(1);
        when(user.getWeight()).thenReturn(1);
        when(user.getEmail()).thenReturn("email");
        when(user.getPassword()).thenReturn("password");
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getLifestyle()).thenReturn(Lifestyle.ACTIVE);
        when(user.getBirthday()).thenReturn(LocalDate.now());
        when(user.getGender()).thenReturn(Gender.OTHER);
        when(user.getFirstName()).thenReturn("name");
        when(user.getLastName()).thenReturn("lastname");

        UserEntity entity = EntityMapper.mapUserToEntityUser(user);

        assertEquals(1, (int) entity.getId());
        assertEquals(1, (int) entity.getWeight());
        assertEquals(1, (int) entity.getHeight());
        assertEquals("email", entity.getEmail());
        assertEquals("password", entity.getPassword());
        assertEquals(Role.USER, entity.getRole());
        assertEquals(Lifestyle.ACTIVE, entity.getLifestyle());
        assertEquals(LocalDate.now(), entity.getBirthday().toLocalDate());
        assertEquals(Gender.OTHER, entity.getGender());
        assertEquals("name", entity.getFirstName());
        assertEquals("lastname", entity.getLastName());
        assertNotNull(entity.getUserGoalEntity());
    }

    @Test
    public void mapUserToUserEntityShouldReturnNotNullUserCaseFemale() {
        when(user.getId()).thenReturn(1);
        when(user.getHeight()).thenReturn(1);
        when(user.getWeight()).thenReturn(1);
        when(user.getEmail()).thenReturn("email");
        when(user.getPassword()).thenReturn("password");
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getLifestyle()).thenReturn(Lifestyle.ACTIVE);
        when(user.getBirthday()).thenReturn(LocalDate.now());
        when(user.getGender()).thenReturn(Gender.FEMALE);
        when(user.getFirstName()).thenReturn("name");
        when(user.getLastName()).thenReturn("lastname");

        UserEntity entity = EntityMapper.mapUserToEntityUser(user);

        assertEquals(1, (int) entity.getId());
        assertEquals(1, (int) entity.getWeight());
        assertEquals(1, (int) entity.getHeight());
        assertEquals("email", entity.getEmail());
        assertEquals("password", entity.getPassword());
        assertEquals(Role.USER, entity.getRole());
        assertEquals(Lifestyle.ACTIVE, entity.getLifestyle());
        assertEquals(LocalDate.now(), entity.getBirthday().toLocalDate());
        assertEquals(Gender.FEMALE, entity.getGender());
        assertEquals("name", entity.getFirstName());
        assertEquals("lastname", entity.getLastName());
        assertNotNull(entity.getUserGoalEntity());
    }

    @Test
    public void mapUserEntityToUserShouldReturnNotNullUser() {
        when(userEntity.getId()).thenReturn(1);
        when(userEntity.getHeight()).thenReturn(1);
        when(userEntity.getWeight()).thenReturn(1);
        when(userEntity.getEmail()).thenReturn("email");
        when(userEntity.getPassword()).thenReturn("password");
        when(userEntity.getRole()).thenReturn(Role.USER);
        when(userEntity.getLifestyle()).thenReturn(Lifestyle.ACTIVE);
        when(userEntity.getBirthday()).thenReturn(new Date(System.currentTimeMillis()));
        when(userEntity.getGender()).thenReturn(Gender.MALE);
        when(userEntity.getFirstName()).thenReturn("name");
        when(userEntity.getLastName()).thenReturn("lastname");
        when(userEntity.getUserGoalEntity()).thenReturn(goal);

        User user = EntityMapper.mapUserEntityToUser(userEntity);

        assertEquals(1, (int) user.getId());
        assertEquals(1, (int) user.getWeight());
        assertEquals(1, (int) user.getHeight());
        assertEquals("email", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals(Lifestyle.ACTIVE, user.getLifestyle());
        assertEquals(LocalDate.now(), user.getBirthday());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals("name", user.getFirstName());
        assertEquals("lastname", user.getLastName());
        assertNotNull(user.getUserGoalEntity());
    }

    private static UserGoalEntity initGoal() {
        return UserGoalEntity.builder()
                .withDailyCarbohydrateGoal(1)
                .withDailyEnergyGoal(1)
                .withDailyFatGoal(1)
                .withDailyProteinGoal(1)
                .withDailyWaterGoal(1)
                .withId(1).build();
    }
}
