package ua.foodtracker.command.impl;

import ua.foodtracker.dto.HomeModelTransferObject;
import ua.foodtracker.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = getUser(request);
        request.setAttribute("homeModel", HomeModelTransferObject.build(user, getDiaryRecordService(request)));
        return "/pages/user/home.jsp";
    }
}

