package ua.foodtracker.command.impl;

import ua.foodtracker.dto.HomeModelTransferObject;
import ua.foodtracker.service.domain.User;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        User user = getUser(request);
        request.setAttribute("homeModel", HomeModelTransferObject.build(user, getDiaryRecordService(request)));
        return "/pages/user/home.jsp";
    }
}

