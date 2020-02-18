package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class AddMealPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/pages/user/add-meal.jsp";
    }
}
