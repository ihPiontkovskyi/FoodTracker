package ua.foodtracker.service.utility;

import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.domain.UserGoal;
import ua.foodtracker.entity.GenderEntity;
import ua.foodtracker.entity.LifestyleEntity;
import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.entity.RoleEntity;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.entity.UserGoalEntity;

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
                .withGender(GenderEntity.getGenderById(user.getGender().getId()))
                .withHeight(user.getHeight())
                .withLifestyle(LifestyleEntity.getLifestyleById(user.getLifestyle().getId()))
                .withPassword(user.getPassword())
                .withRole(RoleEntity.getRoleById(user.getRole().getId()))
                .withWeight(user.getWeight())
                .withUserGoal(calculateUserGoalEntity(user))
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
                .withDate(record.getDate())
                .withWeight(record.getWeight())
                .build();
    }

    private static UserGoalEntity calculateUserGoalEntity(User user) {
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

    private static UserGoal calculateUserGoal(UserGoalEntity entity) {
        return UserGoal.builder()
                .withDailyCarbohydrateGoal(entity.getDailyCarbohydrateGoal())
                .withDailyEnergyGoal(entity.getDailyEnergyGoal())
                .withDailyFatGoal(entity.getDailyFatGoal())
                .withDailyProteinGoal(entity.getDailyProteinGoal())
                .withDailyWaterGoal(entity.getDailyWaterGoal())
                .withId(entity.getId())
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
                .withGender(Gender.getGenderById(userEntity.getGenderEntity().getId()))
                .withHeight(userEntity.getHeight())
                .withLifestyle(Lifestyle.getLifestyleById(userEntity.getLifestyleEntity().getId()))
                .withPassword(userEntity.getPassword())
                .withRole(Role.getRoleById(userEntity.getRoleEntity().getId()))
                .withWeight(userEntity.getWeight())
                .withUserGoal(calculateUserGoal(userEntity.getUserGoalEntity()))
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
                .withDate(recordEntity.getDate())
                .withWeight(recordEntity.getWeight())
                .build();
    }

}
