package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.MealInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static ua.foodtracker.domain.utility.ConvertibleUtility.convertToJsonArray;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/records/byTerm"})
public class MealsInfoCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String term = request.getParameter("term");

        List<MealInfo> list = getMealService(request).findAllByNameStartWith(term).stream()
                .map(meal -> new MealInfo(meal.getId(), meal.getName()))
                .collect(Collectors.toList());
        return convertToJsonArray(list);
    }
}

