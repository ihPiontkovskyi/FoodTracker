package ua.foodtracker.dao.utility;

import ua.foodtracker.entity.GenderEntity;
import ua.foodtracker.entity.LifestyleEntity;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.entity.RoleEntity;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;
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

    public static UserGoalEntity extractUserGoalsFromResultSet(ResultSet resultSet) throws SQLException {
        UserGoalEntity goal = UserGoalEntity.builder()
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

    public static UserEntity extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = UserEntity.builder()
                .withId(resultSet.getInt("users.id"))
                .withEmail(resultSet.getString("email"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withGender(GenderEntity.getGenderById(resultSet.getInt("gender")))
                .withBirthday(resultSet.getDate("birthday"))
                .withHeight(resultSet.getInt("height"))
                .withWeight(resultSet.getInt("weight"))
                .withLifestyle(LifestyleEntity.getLifestyleById(resultSet.getInt("lifestyle")))
                .withPassword(resultSet.getString("password"))
                .withRole(RoleEntity.getRoleById(resultSet.getInt("role")))
                .withUserGoal(extractUserGoalsFromResultSet(resultSet))
                .build();
        if (userEntity.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return userEntity;
    }

    public static MealEntity extractMealFromResultSet(ResultSet resultSet) throws SQLException {
        MealEntity mealEntity = MealEntity.builder().
                withId(resultSet.getInt("meals.id"))
                .withName(resultSet.getString("name"))
                .withCarbohydrates(resultSet.getInt("carbohydrate"))
                .withFat(resultSet.getInt("fat"))
                .withProtein(resultSet.getInt("protein"))
                .withWater(resultSet.getInt("water"))
                .withWeight(resultSet.getInt("weight"))
                .withUser(resultSet.getInt("user_id") == 0 ? null : extractUserFromResultSet(resultSet))
                .build();
        if (mealEntity.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return mealEntity;
    }

    public static RecordEntity extractRecordFromResultSet(ResultSet resultSet) throws SQLException {
        RecordEntity recordEntity = RecordEntity.builder()
                .withId(resultSet.getInt("id"))
                .withMeal(extractMealFromResultSet(resultSet))
                .withDate(resultSet.getDate("date").toLocalDate())
                .withUserId(resultSet.getInt("user_id"))
                .build();
        if (recordEntity.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return recordEntity;
    }
}
