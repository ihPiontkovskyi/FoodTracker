package ua.foodtracker.domain;

import java.util.List;

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
            res.delete(res.length() - 1, 1);
        }
        res.append("]");
        return res.toString();
    }
}
