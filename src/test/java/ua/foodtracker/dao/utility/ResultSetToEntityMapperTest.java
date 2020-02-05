package ua.foodtracker.dao.utility;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.User;
import ua.foodtracker.entity.UserGoal;
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
        UserGoal userGoal = ResultSetToEntityMapper.extractUserGoalsFromResultSet(resultSet);
        assertNotNull(userGoal);
        verify(resultSet, times(6)).getInt(any());
    }

    @Test
    public void extractUserGoalFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        exception.expect(DatabaseInteractionException.class);
        UserGoal userGoal = ResultSetToEntityMapper.extractUserGoalsFromResultSet(resultSet);
    }

    @Test
    public void extractUserFromResultSetShouldReturnUser() throws SQLException {
        mockResultSetForUser();
        User user = ResultSetToEntityMapper.extractUserFromResultSet(resultSet);
        assertNotNull(user);
        verify(resultSet, times(12)).getInt(any());
        verify(resultSet).getDate(any());
        verify(resultSet, times(4)).getString(any());
    }

    @Test
    public void extractUserFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        mockResultSetForUserGoal();
        exception.expect(DatabaseInteractionException.class);
        User user = ResultSetToEntityMapper.extractUserFromResultSet(resultSet);
    }

    @Test
    public void extractMealFromResultSetShouldReturnMeal() throws SQLException {
        mockResultSetForMeal();
        Meal meal = ResultSetToEntityMapper.extractMealFromResultSet(resultSet);
        assertNotNull(meal);
        verify(resultSet, times(7)).getInt(any());
        verify(resultSet, times(1)).getString(any());
    }

    @Test
    public void extractMealFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        mockResultSetForUser();
        exception.expect(DatabaseInteractionException.class);
        Meal meal = ResultSetToEntityMapper.extractMealFromResultSet(resultSet);
    }

    @Test
    public void extractRecordFromResultSetShouldReturnRecord() throws SQLException {
        mockResultSetForRecord();
        Record record = ResultSetToEntityMapper.extractRecordFromResultSet(resultSet);
        assertNotNull(record);
        verify(resultSet, times(21)).getInt(any());
        verify(resultSet, times(2)).getDate(any());
        verify(resultSet, times(5)).getString(any());
    }

    @Test
    public void extractRecordFromResultSetShouldThrowDatabaseInteractionException() throws SQLException {
        exception.expect(DatabaseInteractionException.class);
        Record record = ResultSetToEntityMapper.extractRecordFromResultSet(resultSet);
    }

    @Test
    public void extractRecordFromResultSetShouldThrowDatabaseInteractionExceptionCase2() throws SQLException {
        exception.expect(DatabaseInteractionException.class);
        mockResultSetForMeal();
        Record record = ResultSetToEntityMapper.extractRecordFromResultSet(resultSet);
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
        when(resultSet.getString("email")).thenReturn("email");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("first_name")).thenReturn("firstName");
        when(resultSet.getString("last_name")).thenReturn("lastName");
        when(resultSet.getInt("gender")).thenReturn(1);
        when(resultSet.getInt("role")).thenReturn(1);
        when(resultSet.getInt("lifestyle")).thenReturn(1);
        when(resultSet.getDate("birthday")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getInt("weight")).thenReturn(80);
        when(resultSet.getInt("height")).thenReturn(190);
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
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getDate("date")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getInt("user_id")).thenReturn(1);
    }
}
