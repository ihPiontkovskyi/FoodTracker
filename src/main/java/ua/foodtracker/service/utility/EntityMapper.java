package ua.foodtracker.service.utility;

import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.User;
import ua.foodtracker.entity.UserGoal;
import ua.foodtracker.service.entity.RawMeal;
import ua.foodtracker.service.entity.RawRecord;
import ua.foodtracker.service.entity.RawUser;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class EntityMapper {
    private EntityMapper() {
    }

    public static User mapRawUserToEntityUser(RawUser rawUser) {
        return User.builder()
                .withId(rawUser.getId())
                .withBirthday(Date.valueOf(rawUser.getBirthday()))
                .withEmail(rawUser.getEmail())
                .withFirstName(rawUser.getFirstName())
                .withLastName(rawUser.getLastName())
                .withGender(rawUser.getGender())
                .withHeight(rawUser.getHeight())
                .withLifestyle(rawUser.getLifestyle())
                .withPassword(rawUser.getPassword())
                .withRole(rawUser.getRole())
                .withWeight(rawUser.getWeight())
                .withUserGoal(calculateUserGoal(rawUser))
                .build();
    }

    public static Meal mapRawMealToEntityMeal(RawMeal rawMeal) {
        return Meal.builder()
                .withId(rawMeal.getId())
                .withName(rawMeal.getName())
                .withFat(rawMeal.getFat())
                .withCarbohydrates(rawMeal.getCarbohydrate())
                .withProtein(rawMeal.getProtein())
                .withWater(rawMeal.getWater())
                .withWeight(rawMeal.getWeight())
                .withUser(rawMeal.getUser().getRole() == Role.ADMIN ? null : mapRawUserToEntityUser(rawMeal.getUser()))
                .build();
    }

    public static Record mapRawRecordToEntityRecord(RawRecord rawRecord) {
        return Record.builder()
                .withId(rawRecord.getId())
                .withUserId(rawRecord.getUserId())
                .withMeal(mapRawMealToEntityMeal(rawRecord.getMeal()))
                .withDate(Date.valueOf(rawRecord.getDate()))
                .build();
    }

    private static UserGoal calculateUserGoal(RawUser rawUser) {
        int dailyEnergy;
        int age = Period.between(rawUser.getBirthday(), LocalDate.now()).getYears();
        switch (rawUser.getGender()) {
            case MALE:
                dailyEnergy = (int) ((88.36 + 13.4 * rawUser.getWeight() + 4.8 * rawUser.getHeight() - 5.7 * age) * rawUser.getLifestyle().getCoefficient());
                break;
            case FEMALE:
                dailyEnergy = (int) ((447.6 + 9.2 * rawUser.getWeight() + 3.1 * rawUser.getHeight() - 4.3 * age) * rawUser.getLifestyle().getCoefficient());
                break;
            default:
                dailyEnergy = (int) ((100 + 13 * rawUser.getWeight() + 5 * rawUser.getHeight() - 6 * age) * rawUser.getLifestyle().getCoefficient());
        }
        int sixthPart = dailyEnergy / 6;
        return UserGoal.builder()
                .withDailyEnergyGoal(dailyEnergy)
                .withDailyWaterGoal(2000)
                .withDailyFatGoal(sixthPart / 9)
                .withDailyProteinGoal(sixthPart / 4)
                .withDailyCarbohydrateGoal(sixthPart)
                .build();
    }
}
