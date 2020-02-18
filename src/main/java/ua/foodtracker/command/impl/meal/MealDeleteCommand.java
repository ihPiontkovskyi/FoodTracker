package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/meals/delete"})
public class MealDeleteCommand extends AbstractCommand implements Command {

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
        return "/user/meals";
    }
}
