package ua.foodtracker.domain;

/**
 * The class that represents the meal as an element of the autocomplete
 */
public class MealInfo implements Convertible {
    private final Integer id;
    private final String label;

    public MealInfo(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public String toJson() {
        return "{\"id\":" + id + ",\"label\":\"" + label + "\"}";
    }
}
