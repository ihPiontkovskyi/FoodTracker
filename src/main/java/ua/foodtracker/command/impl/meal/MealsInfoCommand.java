package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.MealInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static ua.foodtracker.domain.ConvertibleUtility.convertToJsonArray;

public class MealsInfoCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String term = request.getParameter("term");

        List<MealInfo> list = getMealService(request).findAllByNameStartWith(term).stream()
                .map(meal -> new MealInfo(meal.getId(), meal.getName()))
                .collect(Collectors.toList());
        return convertToJsonArray(list);
    }
}

