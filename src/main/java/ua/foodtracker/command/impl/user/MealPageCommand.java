package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MealPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        List<Meal> meals = getMealService(request).findAllByPage(request.getParameter("page"));
        request.setAttribute("meals", meals);
        request.setAttribute("pageCount", getMealService(request).pageCount());
        return "/pages/user/meal/meals.jsp";
    }
}
