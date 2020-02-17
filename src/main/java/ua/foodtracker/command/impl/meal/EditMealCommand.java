package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;

import javax.servlet.http.HttpServletRequest;

public class EditMealCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        if (getUser(request).getRole() == Role.ADMIN) {
            getMealService(request).add(getMealFromRequest(request));
        } else {
            Meal meal = getMealService(request).findById(request.getParameter("id"));
            if (meal.getUser().getId().equals(getUser(request).getId())) {
                getMealService(request).add(getMealFromRequest(request));
            } else {
                throw new RuntimeException("Access denied");
            }
        }
        return "/pages/user/meals";
    }

    private Meal getMealFromRequest(HttpServletRequest request) {
        return Meal.builder()
                .withName(request.getParameter("name"))
                .withWeight(getIntParam(request, "weight"))
                .withCarbohydrates(getIntParam(request, "carbohydrate"))
                .withFat(getIntParam(request, "fat"))
                .withProtein(getIntParam(request, "protein"))
                .withWater(getIntParam(request, "water"))
                .withUser(null)
                .withId(getIntParam(request, "id"))
                .build();
    }
}