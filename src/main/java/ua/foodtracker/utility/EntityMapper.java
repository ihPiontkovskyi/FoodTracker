package ua.foodtracker.utility;

import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.User;
import ua.foodtracker.entity.UserGoal;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class , converts values from {@link ResultSet} to specified entity
 */
public class EntityMapper {
    private EntityMapper() {
    }

    public static UserGoal extractUserGoalsFromResultSet(ResultSet resultSet) throws SQLException {
        return UserGoal.builder()
                .withId(resultSet.getInt("user_goals.id"))
                .withDailyEnergyGoal(resultSet.getInt("daily_energy"))
                .withDailyCarbohydrateGoal(resultSet.getInt("daily_carbohydrate"))
                .withDailyFatGoal(resultSet.getInt("daily_fat"))
                .withDailyWaterGoal(resultSet.getInt("daily_water"))
                .withDailyProteinGoal(resultSet.getInt("daily_protein"))
                .build();
    }

    public static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
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
    }

    public static Meal extractMealFromResultSet(ResultSet resultSet) throws SQLException {
        return Meal.builder().
                withId(resultSet.getInt("meals.id"))
                .withName(resultSet.getString("name"))
                .withCarbohydrates(resultSet.getInt("carbohydrate"))
                .withFat(resultSet.getInt("fat"))
                .withProtein(resultSet.getInt("protein"))
                .withWater(resultSet.getInt("water"))
                .withWeight(resultSet.getInt("weight"))
                .withUser(extractUserFromResultSet(resultSet))
                .build();
    }

    public static Record extractRecordFromResultSet(ResultSet resultSet) throws SQLException {
        return Record.builder()
                .withId(resultSet.getInt("id"))
                .withMeal(extractMealFromResultSet(resultSet))
                .withUser(extractUserFromResultSet(resultSet))
                .withDate(resultSet.getDate("date"))
                .build();
    }
}
