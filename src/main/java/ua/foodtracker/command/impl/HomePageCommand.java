package ua.foodtracker.command.impl;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/home"})
public class HomePageCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUser(request);
        request.setAttribute("homeModel", getDiaryRecordService(request).getHomeModel(user));
        return "/WEB-INF/pages/user/home.jsp";
    }
}

