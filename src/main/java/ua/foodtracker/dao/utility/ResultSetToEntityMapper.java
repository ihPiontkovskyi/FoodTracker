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
                .withDailyEnergyGoal(resultSet.getInt("user_goals.daily_energy"))
                .withDailyCarbohydrateGoal(resultSet.getInt("user_goals.daily_carbohydrate"))
                .withDailyFatGoal(resultSet.getInt("user_goals.daily_fat"))
                .withDailyWaterGoal(resultSet.getInt("user_goals.daily_water"))
                .withDailyProteinGoal(resultSet.getInt("user_goals.daily_protein"))
                .build();
        if (goal.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return goal;
    }

    public static UserEntity extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = UserEntity.builder()
                .withId(resultSet.getInt("users.id"))
                .withEmail(resultSet.getString("users.email"))
                .withFirstName(resultSet.getString("users.first_name"))
                .withLastName(resultSet.getString("users.last_name"))
                .withGender(GenderEntity.valueOf(resultSet.getString("users.gender").toUpperCase()))
                .withBirthday(resultSet.getDate("users.birthday"))
                .withHeight(resultSet.getInt("users.height"))
                .withWeight(resultSet.getInt("users.weight"))
                .withLifestyle(LifestyleEntity.valueOf(resultSet.getString("users.lifestyle").toUpperCase()))
                .withPassword(resultSet.getString("users.password"))
                .withRole(RoleEntity.valueOf(resultSet.getString("users.role").toUpperCase()))
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
                .withName(resultSet.getString("meals.name"))
                .withCarbohydrates(resultSet.getInt("meals.carbohydrate"))
                .withFat(resultSet.getInt("meals.fat"))
                .withProtein(resultSet.getInt("meals.protein"))
                .withWater(resultSet.getInt("meals.water"))
                .withWeight(resultSet.getInt("meals.weight"))
                .withUser(resultSet.getInt("meals.user_id") == 0 ? null : extractUserFromResultSet(resultSet))
                .build();
        if (mealEntity.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return mealEntity;
    }

    public static RecordEntity extractRecordFromResultSet(ResultSet resultSet) throws SQLException {
        RecordEntity recordEntity = RecordEntity.builder()
                .withId(resultSet.getInt("records.id"))
                .withMeal(extractMealFromResultSet(resultSet))
                .withDate(resultSet.getDate("records.date").toLocalDate())
                .withUserId(resultSet.getInt("records.user_id"))
                .withWeight(resultSet.getInt("records.weight"))
                .build();
        if (recordEntity.getId().equals(0)) {
            throw new DatabaseInteractionException(ERROR_MESSAGE);
        }
        return recordEntity;
    }
}
