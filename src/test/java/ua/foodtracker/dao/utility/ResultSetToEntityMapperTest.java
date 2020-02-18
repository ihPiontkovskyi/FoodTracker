package ua.foodtracker.dao.utility;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultSetToEntityMapperTest {

    @Mock
    private ResultSet resultSet;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @After
    public void resetMock() {
        reset(resultSet);
    }

    @Test
    public void extractUserGoalFromResultSetShouldReturnUserGoal() throws SQLException {
        mockResultSetForUserGoal();
        UserGoalEntity userGoalEntity = ResultSetToEntityMapper.extractUserGoalsFromResultSet(resultSet);
        assertNotNull(userGoalEntity);
        verify(resultSet, times(6)).getInt(any());
    }

    @Test
    public void extractUserGoalFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        exception.expect(DatabaseInteractionException.class);
        UserGoalEntity userGoalEntity = ResultSetToEntityMapper.extractUserGoalsFromResultSet(resultSet);
    }

    @Test
    public void extractUserFromResultSetShouldReturnUser() throws SQLException {
        mockResultSetForUser();
        UserEntity userEntity = ResultSetToEntityMapper.extractUserFromResultSet(resultSet);
        assertNotNull(userEntity);
        verify(resultSet, times(9)).getInt(any());
        verify(resultSet).getDate(any());
        verify(resultSet, times(7)).getString(any());
    }

    @Test
    public void extractMealFromResultSetShouldReturnMeal() throws SQLException {
        mockResultSetForMeal();
        MealEntity mealEntity = ResultSetToEntityMapper.extractMealFromResultSet(resultSet);
        assertNotNull(mealEntity);
        verify(resultSet, times(7)).getInt(any());
        verify(resultSet, times(1)).getString(any());
    }

    @Test
    public void extractMealFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        mockResultSetForUser();
        exception.expect(DatabaseInteractionException.class);
        MealEntity mealEntity = ResultSetToEntityMapper.extractMealFromResultSet(resultSet);
    }

    @Test
    public void extractRecordFromResultSetShouldReturnRecord() throws SQLException {
        mockResultSetForRecord();
        RecordEntity recordEntity = ResultSetToEntityMapper.extractRecordFromResultSet(resultSet);
        assertNotNull(recordEntity);
        verify(resultSet, times(10)).getInt(any());
        verify(resultSet, times(1)).getDate(any());
        verify(resultSet, times(1)).getString(any());
    }

    private void mockResultSetForUserGoal() throws SQLException {
        when(resultSet.getInt("user_goals.id")).thenReturn(1);
        when(resultSet.getInt("daily_energy")).thenReturn(2000);
        when(resultSet.getInt("daily_carbohydrate")).thenReturn(400);
        when(resultSet.getInt("daily_fat")).thenReturn(60);
        when(resultSet.getInt("daily_water")).thenReturn(2000);
        when(resultSet.getInt("daily_protein")).thenReturn(40);
    }

    private void mockResultSetForUser() throws SQLException {
        mockResultSetForUserGoal();
        when(resultSet.getInt("users.id")).thenReturn(1);
        when(resultSet.getString("users.email")).thenReturn("email");
        when(resultSet.getString("users.password")).thenReturn("password");
        when(resultSet.getString("users.first_name")).thenReturn("firstName");
        when(resultSet.getString("users.last_name")).thenReturn("lastName");
        when(resultSet.getString("users.gender")).thenReturn("OTHER");
        when(resultSet.getString("users.role")).thenReturn("USER");
        when(resultSet.getString("users.lifestyle")).thenReturn("ACTIVE");
        when(resultSet.getDate("users.birthday")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getInt("users.weight")).thenReturn(80);
        when(resultSet.getInt("users.height")).thenReturn(190);
    }

    private void mockResultSetForMeal() throws SQLException {
        mockResultSetForUserGoal();
        mockResultSetForUser();
        when(resultSet.getInt("meals.id")).thenReturn(1);
        when(resultSet.getInt("carbohydrate")).thenReturn(10);
        when(resultSet.getInt("fat")).thenReturn(10);
        when(resultSet.getInt("protein")).thenReturn(10);
        when(resultSet.getInt("water")).thenReturn(20);
        when(resultSet.getInt("weight")).thenReturn(200);
        when(resultSet.getString("name")).thenReturn("name");
    }

    private void mockResultSetForRecord() throws SQLException {
        mockResultSetForUserGoal();
        mockResultSetForUser();
        mockResultSetForMeal();
        when(resultSet.getInt("records.id")).thenReturn(1);
        when(resultSet.getInt("records.weight")).thenReturn(1);
        when(resultSet.getDate("records.date")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getInt("records.user_id")).thenReturn(1);
    }
}
