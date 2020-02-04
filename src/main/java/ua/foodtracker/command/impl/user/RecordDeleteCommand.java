package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecordDeleteCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        getDiaryRecordService(request).delete(request.getParameter("id"));
        return "/pages/user/records";
    }
}
