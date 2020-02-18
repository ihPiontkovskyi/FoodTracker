package ua.foodtracker.command.impl;

import ua.foodtracker.command.Command;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

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
        String paramValue = request.getParameter(param);
        if (paramValue == null) {
            return null;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return null;
        }
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

    protected Locale getLocale(HttpServletRequest request) {
        return Locale.forLanguageTag(request.getSession(false).getAttribute("locale").toString());
    }

    protected MealService getMealService(HttpServletRequest request) {
        MealService service = (MealService) request.getServletContext().getAttribute("ua.foodtracker.service.MealService");
        service.setLocale(getLocale(request));
        return service;
    }

    protected RecordService getDiaryRecordService(HttpServletRequest request) {
        RecordService service = (RecordService) request.getServletContext().getAttribute("ua.foodtracker.service.RecordService");
        service.setLocale(getLocale(request));
        return service;
    }

    protected UserService getUserService(HttpServletRequest request) {
        UserService service = (UserService) request.getServletContext().getAttribute("ua.foodtracker.service.UserService");
        service.setLocale(getLocale(request));
        return service;
    }
}
