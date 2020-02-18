package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = "/foodtracker.ua/user/meal-edit")
public class EditMealPageCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("meal", getMealService(request).findById(request.getParameter("id")));
        return "/WEB-INF/pages/user/edit-meal.jsp";
    }
}