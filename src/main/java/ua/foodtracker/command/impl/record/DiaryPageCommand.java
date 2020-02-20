package ua.foodtracker.command.impl.record;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/records"})
public class DiaryPageCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LocalDate date = getDateParamOrNow(request, "date");
        User user = getUser(request);
        session.setAttribute("date", date);
        List<Record> dailyRecord = getDiaryRecordService(request).getRecordsByDate(user, request.getParameter("date"));
        request.setAttribute("records", dailyRecord);
        final DailySums dailySums = getDiaryRecordService(request).calculateDailySums(user, date.toString());
        request.setAttribute("dateSums", dailySums);
        if (dailySums.getSumEnergy() > user.getUserGoal().getDailyEnergyGoal()) {
            request.setAttribute("exceedingTheGoal", true);
            request.setAttribute("exceedingValue", dailySums.getSumEnergy() - user.getUserGoal().getDailyEnergyGoal());
        }
        return "/WEB-INF/pages/user/records.jsp";
    }
}
