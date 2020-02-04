package ua.foodtracker.dao.utility;

import ua.foodtracker.dao.entity.Gender;
import ua.foodtracker.dao.entity.Lifestyle;
import ua.foodtracker.dao.entity.Meal;
import ua.foodtracker.dao.entity.Record;
import ua.foodtracker.dao.entity.Role;
import ua.foodtracker.dao.entity.User;
import ua.foodtracker.dao.entity.UserGoal;
import ua.foodtracker.exception.DatabaseInteractionException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class , converts values from {@link ResultSet} to specified entity
 */
public class ResultSetToEntityMapper {

    private static final String ERROR_MESSAGE = "Cannot extract entity from result set";

    private ResultSetToEntityMapper() {
    }

    public static UserGoal extractUserGoalsFromResultSet(ResultSet resultSet) throws SQLException {
        UserGoal goal = UserGoal.builder()
                .withId(resultSet.getInt("user_goals.id"))
                .withDailyEnergyGoal(resultSet.getInt("daily_energy"))
                .withDailyCarbohydrateGoal(resultSet.getInt("daily_carbohydrate"))
                .withDailyFatGoal(resultSet.getInt("daily_fat"))
                .withDailyWaterGoal(resultSet.getInt("daily_water"))
                .withDailyProteinGoal(resultSet.getInt("daily_protein"))
                .build();
        if (goal.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return goal;
    }

    public static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .withId(resultSet.getInt("users.id"))
                .withEmail(resultSet.getString("email"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withGender(Gender.getGenderById(resultSet.getInt("gender")))
                .withBirthday(resultSet.getDate("birthday"))
                .withHeight(resultSet.getInt("height"))
                .withWeight(resultSet.getInt("weight"))
                .withLifestyle(Lifestyle.getLifestyleById(resultSet.getInt("lifestyle")))
                .withPassword(resultSet.getString("password"))
                .withRole(Role.getGenderById(resultSet.getInt("role")))
                .withUserGoal(extractUserGoalsFromResultSet(resultSet))
                .build();
        if (user.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return user;
    }

    public static Meal extractMealFromResultSet(ResultSet resultSet) throws SQLException {
        Meal meal = Meal.builder().
                withId(resultSet.getInt("meals.id"))
                .withName(resultSet.getString("name"))
                .withCarbohydrates(resultSet.getInt("carbohydrate"))
                .withFat(resultSet.getInt("fat"))
                .withProtein(resultSet.getInt("protein"))
                .withWater(resultSet.getInt("water"))
                .withWeight(resultSet.getInt("weight"))
                .withUser(extractUserFromResultSet(resultSet))
                .build();
        if (meal.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return meal;
    }

    public static Record extractRecordFromResultSet(ResultSet resultSet) throws SQLException {
        Record record = Record.builder()
                .withId(resultSet.getInt("id"))
                .withMeal(extractMealFromResultSet(resultSet))
                .withDate(resultSet.getDate("date"))
                .withUserId(resultSet.getInt("user_id"))
                .build();
        if (record.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return record;
    }
}
