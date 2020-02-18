package ua.foodtracker.command.impl.record;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;

import javax.servlet.http.HttpServletRequest;

public class AddRecordCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        Meal meal = getMealService(request).findById(request.getParameter("meal_id"));
        Record record = Record.builder()
                .withMeal(meal)
                .withWeight(getIntParam(request, "weight"))
                .withDate(getDateParamOrNow(request, "date"))
                .withUserId(getUser(request).getId())
                .build();
        getDiaryRecordService(request).add(record);
        return "/user/records?date=" + record.getDate().toString();
    }
}