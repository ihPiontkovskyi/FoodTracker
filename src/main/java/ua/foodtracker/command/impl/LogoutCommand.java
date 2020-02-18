package ua.foodtracker.command.impl;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/logout"})
public class LogoutCommand extends AbstractCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return "/login-page";
    }
}
