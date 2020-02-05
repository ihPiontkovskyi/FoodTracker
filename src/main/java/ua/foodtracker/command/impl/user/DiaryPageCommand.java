package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.dto.DailySumsTransferObject;
import ua.foodtracker.entity.RecordEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DiaryPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        List<RecordEntity> dailyRecordEntities = getDiaryRecordService(request).getRecordsByDate(getUser(request).getId(), getDateParamFromSessionOrNow(request, "currentDate"));
        request.setAttribute("records", dailyRecordEntities);
        request.setAttribute("dateSums", DailySumsTransferObject.build(dailyRecordEntities));
        return "/pages/user/records.jsp";
    }
}
