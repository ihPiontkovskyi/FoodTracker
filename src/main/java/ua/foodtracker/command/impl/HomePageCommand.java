package ua.foodtracker.command.impl;

import ua.foodtracker.domain.HomeModel;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUser(request);
        request.setAttribute("homeModel", HomeModel.build(user, getDiaryRecordService(request)));
        return "/pages/user/home.jsp";
    }
}

