package ua.foodtracker.command.impl;

import javax.servlet.http.HttpServletRequest;

public class RegisterPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/pages/register.jsp";
    }
}
