package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class ProfilePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/pages/user/profile.jsp";
    }
}
