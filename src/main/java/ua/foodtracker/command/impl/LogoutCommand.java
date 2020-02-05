package ua.foodtracker.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).invalidate();
        return "/pages/login.jsp";
    }
}
