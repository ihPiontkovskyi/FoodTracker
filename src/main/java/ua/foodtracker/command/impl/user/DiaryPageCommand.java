package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.dto.DailySumsTransferObject;
import ua.foodtracker.entity.Record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DiaryPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Record> dailyRecords = getDiaryRecordService(request).getRecordsByDate(getUser(request).getId(), getDateParamOrNov(request));
        request.setAttribute("records", dailyRecords);
        request.setAttribute("dateSums", DailySumsTransferObject.build(dailyRecords));
        return "/pages/user/records.jsp";
    }
}
