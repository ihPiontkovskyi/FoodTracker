package ua.foodtracker.command.impl.record;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/records/delete"})
public class RecordDeleteCommand extends AbstractCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        getDiaryRecordService(request).delete(request.getParameter("id"));
        return "/user/records";
    }
}
