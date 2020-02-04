package ua.foodtracker.command;

import org.apache.log4j.Logger;
import ua.foodtracker.dao.entity.User;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public interface Command {
    Logger LOGGER = Logger.getLogger(Command.class);

    String execute(HttpServletRequest request, HttpServletResponse response);

    default User getUser(HttpServletRequest request) {
        return (User) request.getSession(false).getAttribute("user");
    }

    default Integer getIntParamOrDefault(HttpServletRequest request, String param, Integer defaultInt) {
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

    default Integer getIntParam(HttpServletRequest request, String param) {
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

    default String getStringParamOrDefault(HttpServletRequest request, String param, String defaultValue) {
        String paramValue = decodeParameter(request.getParameter(param));
        if (paramValue == null) {
            return defaultValue;
        }
        return paramValue.trim();
    }

    default LocalDate getDateParamOrNov(HttpServletRequest request) {
        if (request.getSession(false).getAttribute("currentDate") != null) {
            try {
                return (LocalDate)request.getSession(false).getAttribute("currentDate");
            } catch (DateTimeParseException ex) {
                return LocalDate.now();
            }
        }
        return LocalDate.now();
    }

    default Locale getLocale(HttpServletRequest request) {
        return Locale.forLanguageTag(request.getSession(false).getAttribute("locale").toString());
    }

    default String decodeParameter(String parameter) {
        return new String(parameter.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    default MealService getMealService(HttpServletRequest request) {
        MealService service = (MealService) request.getServletContext().getAttribute("ua.foodtracker.service.MealService");
        service.setLocale(getLocale(request));
        return service;
    }

    default RecordService getDiaryRecordService(HttpServletRequest request) {
        RecordService service = (RecordService) request.getServletContext().getAttribute("ua.foodtracker.service.RecordService");
        service.setLocale(getLocale(request));
        return service;
    }

    default UserService getUserService(HttpServletRequest request) {
        UserService service = (UserService) request.getServletContext().getAttribute("ua.foodtracker.service.UserService");
        service.setLocale(getLocale(request));
        return service;
    }

    default void sendError(HttpServletResponse response, int errorCode, String message) {
        try {
            response.sendError(errorCode, message);
        } catch (IOException e) {
            LOGGER.warn("Cannot send error with message, cause:", e);
        }
    }
}
