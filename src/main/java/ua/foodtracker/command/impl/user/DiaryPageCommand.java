package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.dto.DailySumsTransferObject;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.service.domain.Record;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DiaryPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        List<Record> dailyRecord = getDiaryRecordService(request).getRecordsByDate(getUser(request).getId(), getDateParamFromSessionOrNow(request, "currentDate"));
        request.setAttribute("records", dailyRecord);
        request.setAttribute("dateSums", DailySumsTransferObject.build(dailyRecord));
        return "/pages/user/records.jsp";
    }
}
