package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;

import javax.servlet.http.HttpServletRequest;

public class AddMealCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        Meal meal = Meal.builder()
                .withName(request.getParameter("name"))
                .withWeight(getIntParam(request, "weight"))
                .withCarbohydrates(getIntParam(request, "carbohydrate"))
                .withFat(getIntParam(request, "fat"))
                .withProtein(getIntParam(request, "protein"))
                .withWater(getIntParam(request, "water"))
                .withUser(getUser(request))
                .build();
        getMealService(request).add(meal);
        return "/pages/user/meals";
    }
}