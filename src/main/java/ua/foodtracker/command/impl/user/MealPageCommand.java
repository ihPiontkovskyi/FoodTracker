package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.Command;
import ua.foodtracker.dao.entity.Meal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MealPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Meal> meals = getMealService(request).findAllByPage((Integer) request.getSession(false).getAttribute("currentPage"), getUser(request).getId());
        request.setAttribute("meals", meals);
        request.setAttribute("pageCount", getMealService(request).pageCount());
        return "/pages/user/meals.jsp";
    }
}
