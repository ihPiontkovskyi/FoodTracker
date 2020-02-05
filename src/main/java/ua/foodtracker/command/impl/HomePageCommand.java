package ua.foodtracker.command.impl;

import ua.foodtracker.dto.HomeModelTransferObject;
import ua.foodtracker.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        UserEntity userEntity = getUser(request);
        request.setAttribute("homeModel", HomeModelTransferObject.build(userEntity, getDiaryRecordService(request)));
        return "/pages/user/home.jsp";
    }
}

