package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.entity.Role;
import ua.foodtracker.service.domain.Meal;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class MealDeleteCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        if (getUser(request).getRole() == Role.ADMIN) {
            getMealService(request).delete(request.getParameter("id"));
        } else {
            Optional<Meal> meal = getMealService(request).findById(request.getParameter("id"));
            if (meal.isPresent() && meal.get().getUser().getId().equals(getUser(request).getId())) {
                getMealService(request).delete(request.getParameter("id"));
            }
        }
        return "/pages/user/meals";
    }
}
