package ua.foodtracker.command.impl;

import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).removeAttribute("user");
        return "/pages/login.jsp";
    }
}
