package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = "/foodtracker.ua/user/meal-add")
public class AddMealPageCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/pages/user/add-meal.jsp";
    }
}
