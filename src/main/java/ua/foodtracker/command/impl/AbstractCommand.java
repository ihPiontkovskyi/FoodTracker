package ua.foodtracker.command.impl;

import org.apache.log4j.Logger;
import ua.foodtracker.command.Command;
import ua.foodtracker.entity.UserEntity;
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

public abstract class AbstractCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(Command.class);

    protected UserEntity getUser(HttpServletRequest request) {
        return (UserEntity) request.getSession(false).getAttribute("user");
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

    protected String getStringParamOrDefault(HttpServletRequest request, String param, String defaultValue) {
        String paramValue = decodeParameter(request.getParameter(param));
        if (paramValue == null) {
            return defaultValue;
        }
        return paramValue.trim();
    }

    protected LocalDate getDateParamFromSessionOrNow(HttpServletRequest request, String param) {
        if (request.getSession(false).getAttribute(param) != null) {
            return (LocalDate) request.getSession(false).getAttribute(param);
        }
        return LocalDate.now();
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

    protected String decodeParameter(String parameter) {
        return new String(parameter.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
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

    protected void sendError(HttpServletResponse response, int errorCode, String message) {
        try {
            response.sendError(errorCode, message);
        } catch (IOException e) {
            LOGGER.warn("Cannot send error with message, cause:", e);
        }
    }
}
