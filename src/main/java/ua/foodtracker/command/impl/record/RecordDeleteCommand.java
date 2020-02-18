package ua.foodtracker.command.impl.record;

import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class RecordDeleteCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        getDiaryRecordService(request).delete(request.getParameter("id"));
        return "/user/records";
    }
}
