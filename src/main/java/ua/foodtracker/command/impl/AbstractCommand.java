package ua.foodtracker.command.impl;

import ua.foodtracker.command.Command;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class AbstractCommand implements Command {

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession(false).getAttribute("user");
    }

    protected Integer getIntParamOrDefault(HttpServletRequest request, String param, Integer defaultInt) {
        String paramValue = request.getParameter(param);
        if (paramValue == null) {
            return defaultInt;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return defaultInt;
        }
    }

    protected Integer getIntParam(HttpServletRequest request, String param) {
        return getIntParamOrDefault(request, param, null);
    }

    protected LocalDate getDateParamOrNow(HttpServletRequest request, String param) {
        String dateValue = request.getParameter(param);
        if (dateValue != null) {
            try {
                return LocalDate.parse(dateValue);
            } catch (DateTimeParseException ex) {
                return LocalDate.now();
            }
        }
        return LocalDate.now();
    }

    protected MealService getMealService(HttpServletRequest request) {
        return (MealService) request.getServletContext().getAttribute("ua.foodtracker.service.MealService");
    }

    protected RecordService getDiaryRecordService(HttpServletRequest request) {
        return (RecordService) request.getServletContext().getAttribute("ua.foodtracker.service.RecordService");
    }

    protected UserService getUserService(HttpServletRequest request) {
        return (UserService) request.getServletContext().getAttribute("ua.foodtracker.service.UserService");
    }
}
