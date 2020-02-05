package ua.foodtracker.dto;

import ua.foodtracker.entity.MealEntity;
import ua.foodtracker.entity.RecordEntity;

import java.util.List;

public final class DailySumsTransferObject {
    private int sumEnergy;
    private int sumProtein;
    private int sumFat;
    private int sumCarbohydrates;
    private int sumWater;

    private DailySumsTransferObject() {
    }

    public int getSumEnergy() {
        return sumEnergy;
    }

    public int getSumProtein() {
        return sumProtein;
    }

    public int getSumFat() {
        return sumFat;
    }

    public int getSumCarbohydrates() {
        return sumCarbohydrates;
    }

    public int getSumWater() {
        return sumWater;
    }

    public static DailySumsTransferObject build(List<? extends RecordEntity> list) {
        DailySumsTransferObject dsto = new DailySumsTransferObject();
        for (RecordEntity recordEntity : list) {
            MealEntity mealEntity = recordEntity.getMealEntity();
            dsto.sumEnergy += mealEntity.calculateEnergy();
            dsto.sumProtein += mealEntity.getProtein();
            dsto.sumFat += mealEntity.getFat();
            dsto.sumCarbohydrates += mealEntity.getCarbohydrate();
            dsto.sumWater += mealEntity.getWater();

        }
        return dsto;
    }
}
