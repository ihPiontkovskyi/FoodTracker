package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecordDeleteCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        getDiaryRecordService(request).delete(request.getParameter("id"));
        return "/pages/user/records";
    }
}
