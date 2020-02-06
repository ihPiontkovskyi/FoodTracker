package ua.foodtracker.service.utility;

import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;
import ua.foodtracker.service.domain.Meal;
import ua.foodtracker.service.domain.Record;
import ua.foodtracker.service.domain.User;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class EntityMapper {
    private EntityMapper() {
    }

    public static UserEntity mapUserToEntityUser(User user) {
        if (user == null) {
            return null;
        }
        return UserEntity.builder()
                .withId(user.getId())
                .withBirthday(Date.valueOf(user.getBirthday()))
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withGender(user.getGender())
                .withHeight(user.getHeight())
                .withLifestyle(user.getLifestyle())
                .withPassword(user.getPassword())
                .withRole(user.getRole())
                .withWeight(user.getWeight())
                .withUserGoal(calculateUserGoal(user))
                .build();
    }

    public static MealEntity mapMealToEntityMeal(Meal meal) {
        return MealEntity.builder()
                .withId(meal.getId())
                .withName(meal.getName())
                .withFat(meal.getFat())
                .withCarbohydrates(meal.getCarbohydrate())
                .withProtein(meal.getProtein())
                .withWater(meal.getWater())
                .withWeight(meal.getWeight())
                .withUser(mapUserToEntityUser(meal.getUser()))
                .build();
    }

    public static RecordEntity mapRecordToEntityRecord(Record record) {
        return RecordEntity.builder()
                .withId(record.getId())
                .withUserId(record.getUserId())
                .withMeal(mapMealToEntityMeal(record.getMeal()))
                .withDate(Date.valueOf(record.getDate()))
                .build();
    }

    private static UserGoalEntity calculateUserGoal(User user) {
        int dailyEnergy;
        int age = Period.between(user.getBirthday(), LocalDate.now()).getYears();
        switch (user.getGender()) {
            case MALE:
                dailyEnergy = (int) ((88.36 + 13.4 * user.getWeight() + 4.8 * user.getHeight() - 5.7 * age) * user.getLifestyle().getCoefficient());
                break;
            case FEMALE:
                dailyEnergy = (int) ((447.6 + 9.2 * user.getWeight() + 3.1 * user.getHeight() - 4.3 * age) * user.getLifestyle().getCoefficient());
                break;
            default:
                dailyEnergy = (int) ((100 + 13 * user.getWeight() + 5 * user.getHeight() - 6 * age) * user.getLifestyle().getCoefficient());
        }
        int sixthPart = dailyEnergy / 6;
        return UserGoalEntity.builder()
                .withDailyEnergyGoal(dailyEnergy)
                .withDailyWaterGoal(2000)
                .withDailyFatGoal(sixthPart / 9)
                .withDailyProteinGoal(sixthPart / 4)
                .withDailyCarbohydrateGoal(sixthPart)
                .build();
    }

    public static User mapUserEntityToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return User.builder()
                .withId(userEntity.getId())
                .withBirthday(userEntity.getBirthday().toLocalDate())
                .withEmail(userEntity.getEmail())
                .withFirstName(userEntity.getFirstName())
                .withLastName(userEntity.getLastName())
                .withGender(userEntity.getGender())
                .withHeight(userEntity.getHeight())
                .withLifestyle(userEntity.getLifestyle())
                .withPassword(userEntity.getPassword())
                .withRole(userEntity.getRole())
                .withWeight(userEntity.getWeight())
                .withUserGoal(userEntity.getUserGoalEntity())
                .build();
    }

    public static Meal mapEntityMealToMeal(MealEntity meal) {
        return Meal.builder()
                .withId(meal.getId())
                .withName(meal.getName())
                .withFat(meal.getFat())
                .withCarbohydrates(meal.getCarbohydrate())
                .withProtein(meal.getProtein())
                .withWater(meal.getWater())
                .withWeight(meal.getWeight())
                .withUser(mapUserEntityToUser(meal.getUserEntity()))
                .build();
    }

    public static Record mapEntityRecordToRecord(RecordEntity recordEntity) {
        return Record.builder()
                .withId(recordEntity.getId())
                .withUserId(recordEntity.getUserId())
                .withMeal(mapEntityMealToMeal(recordEntity.getMealEntity()))
                .withDate(recordEntity.getDate().toLocalDate())
                .build();
    }

}
