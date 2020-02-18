package ua.foodtracker.command.impl;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/login"})
public class LoginCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUserService(request).login(request.getParameter("username"), request.getParameter("pass"));
        request.getSession(true).setAttribute("user", user);
        return "/user/home";
    }
}
