package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.exception.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/meals/edit-meal"})
public class EditMealCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (getUser(request).getRole() == Role.ADMIN) {
            getMealService(request).add(getMealFromRequest(request));
        } else {
            Meal meal = getMealService(request).findById(request.getParameter("id"));
            if (meal.getUser().getId().equals(getUser(request).getId())) {
                getMealService(request).add(getMealFromRequest(request));
            } else {
                throw new AccessDeniedException("access.denied");
            }
        }
        return "/user/meals";
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