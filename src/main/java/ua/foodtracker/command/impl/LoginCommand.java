package ua.foodtracker.command.impl;

import ua.foodtracker.service.domain.User;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUserService(request).login(request.getParameter("username"), request.getParameter("pass"));
        request.getSession(true).setAttribute("user", user);
        return "/pages/user/home";
    }
}
