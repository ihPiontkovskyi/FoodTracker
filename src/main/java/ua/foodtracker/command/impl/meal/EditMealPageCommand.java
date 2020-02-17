package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class EditMealPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("meal", getMealService(request).findById(request.getParameter("id")));
        return "/pages/user/meal/edit.jsp";
    }
}