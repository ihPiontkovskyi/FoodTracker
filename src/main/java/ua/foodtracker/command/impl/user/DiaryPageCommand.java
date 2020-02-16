package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.Record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

public class DiaryPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session.getAttribute("date") == null) {
            session.setAttribute("date", LocalDate.now());
        }
        List<Record> dailyRecord = getDiaryRecordService(request).getRecordsByDate(getUser(request).getId(), request.getParameter("date"));
        request.setAttribute("records", dailyRecord);
        request.setAttribute("dateSums", DailySums.build(dailyRecord));
        return "/pages/user/records.jsp";
    }
}
