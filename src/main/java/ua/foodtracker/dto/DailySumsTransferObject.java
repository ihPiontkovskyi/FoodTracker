package ua.foodtracker.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;

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

    public JsonObject toJson() {
        JsonObject dailySums = new JsonObject();
        dailySums.add("sumEnergy", new JsonPrimitive(sumEnergy));
        dailySums.add("sumProtein", new JsonPrimitive(sumProtein));
        dailySums.add("sumFat", new JsonPrimitive(sumFat));
        dailySums.add("sumCarbohydrates", new JsonPrimitive(sumCarbohydrates));
        dailySums.add("sumWater", new JsonPrimitive(sumWater));
        return dailySums;
    }
}
