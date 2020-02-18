package ua.foodtracker.command.impl;

import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUser(request);
        request.setAttribute("homeModel", getDiaryRecordService(request).getHomeModel(user));
        return "/WEB-INF/pages/user/home.jsp";
    }
}

