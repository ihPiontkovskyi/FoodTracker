package ua.foodtracker.domain;

import java.util.List;

public final class DailySums {
    private int sumEnergy;
    private int sumProtein;
    private int sumFat;
    private int sumCarbohydrates;
    private int sumWater;

    private DailySums() {
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

    public static DailySums build(List<? extends Record> list) {
        DailySums dsto = new DailySums();
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
