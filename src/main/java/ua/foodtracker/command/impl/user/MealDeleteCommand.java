package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.Command;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class MealDeleteCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
