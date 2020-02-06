package ua.foodtracker.command.impl;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return "/pages/login.jsp";
    }
}
