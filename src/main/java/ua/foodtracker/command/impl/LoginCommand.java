package ua.foodtracker.command.impl;

import ua.foodtracker.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = getUserService(request).login(request.getParameter("username"), request.getParameter("pass"));
        request.getSession(true).setAttribute("user", user);
        return "/pages/user/home";
    }
}
