package ua.foodtracker.domain.utility;

import ua.foodtracker.domain.Convertible;

import java.util.List;

/**
 * Utility class , converts values from domain object to json string
 */

public class ConvertibleUtility {
    private ConvertibleUtility() {
    }

    public static String convertToJsonArray(List<? extends Convertible> list) {
        StringBuilder res = new StringBuilder("[");
        list.forEach(o -> {
            res.append(o.toJson());
            res.append(",");
        });
        if (res.length() > 1) {
            res.delete(res.length() - 1, res.length());
        }
        res.append("]");
        return res.toString();
    }
}
