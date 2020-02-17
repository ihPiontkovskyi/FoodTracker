package ua.foodtracker.command.impl.record;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

public class DiaryPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LocalDate date = getDateParamOrNow(request, "date");
        User user = getUser(request);
        session.setAttribute("date", date);
        List<Record> dailyRecord = getDiaryRecordService(request).getRecordsByDate(user, request.getParameter("date"));
        request.setAttribute("records", dailyRecord);
        request.setAttribute("dateSums", getDiaryRecordService(request).calculateDailySums(user, date.toString()));
        return "/pages/user/records.jsp";
    }
}
