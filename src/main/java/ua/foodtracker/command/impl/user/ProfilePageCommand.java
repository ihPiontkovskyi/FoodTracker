package ua.foodtracker.command.impl.user;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/profile"})
public class ProfilePageCommand extends AbstractCommand implements Command  {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/pages/user/profile.jsp";
    }
}
