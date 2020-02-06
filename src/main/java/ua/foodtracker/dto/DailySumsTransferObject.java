package ua.foodtracker.dto;

import ua.foodtracker.service.domain.Meal;
import ua.foodtracker.service.domain.Record;

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

    public static DailySumsTransferObject build(List<? extends Record> list) {
        DailySumsTransferObject dsto = new DailySumsTransferObject();
        for (Record record : list) {
            Meal meal = record.getMeal();
            dsto.sumEnergy += meal.calculateEnergy();
            dsto.sumProtein += meal.getProtein();
            dsto.sumFat += meal.getFat();
            dsto.sumCarbohydrates += meal.getCarbohydrate();
            dsto.sumWater += meal.getWater();

        }
        return dsto;
    }
}
