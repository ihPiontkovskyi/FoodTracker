package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;

import javax.servlet.http.HttpServletRequest;

public class MealDeleteCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        if (getUser(request).getRole() == Role.ADMIN) {
            getMealService(request).delete(request.getParameter("id"));
        } else {
            Meal meal = getMealService(request).findById(request.getParameter("id"));
            if (meal.getUser().getId().equals(getUser(request).getId())) {
                getMealService(request).delete(request.getParameter("id"));
            }
        }
        return "/pages/user/meals";
    }
}
