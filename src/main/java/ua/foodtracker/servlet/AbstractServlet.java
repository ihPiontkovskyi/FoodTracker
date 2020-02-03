package ua.foodtracker.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.User;
import ua.foodtracker.exception.RequestProcessingException;
import ua.foodtracker.exception.ResponseProcessingError;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AbstractServlet extends HttpServlet {

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractServlet.class);
    public static final String ERROR_FORWARD_MESSAGE = "Cannot process request ['{}']; Message: ";
    public static final String ERROR_REDIRECT_MESSAGE = "Cannot process response ['{}']; Message: ";

    protected void setCurrentUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.Attributes.CURRENT_USER, user);
    }

    protected void unsetCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.Attributes.CURRENT_USER);
    }

    protected Integer getUserId(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute(Constants.Attributes.CURRENT_USER);
        if (user == null) {
            return null;
        }
        return user.getId();
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

    protected String getStringParam(HttpServletRequest request, String param) {
        String paramValue = decodeParameter(request.getParameter(param));
        if (paramValue == null) {
            return null;
        }
        return paramValue.trim();
    }

    protected MealService getMealService() {
        return (MealService) getServletContext().getAttribute(Constants.Attributes.MEAL_SERVICE);
    }

    protected RecordService getDiaryRecordService() {
        return (RecordService) getServletContext().getAttribute(Constants.Attributes.DIARY_RECORD_SERVICE);
    }

    protected UserService getUserService() {
        return (UserService) getServletContext().getAttribute(Constants.Attributes.USER_SERVICE);
    }

    protected void forward(String page, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(page).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.warn(ERROR_FORWARD_MESSAGE, request.getRequestURI(), e);
            throw new RequestProcessingException(ERROR_FORWARD_MESSAGE, e);
        }
    }

    protected void redirectTo(String uri, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + uri);
        } catch (IOException e) {
            LOGGER.warn(ERROR_REDIRECT_MESSAGE, request.getRequestURI(), e);
            throw new ResponseProcessingError(ERROR_REDIRECT_MESSAGE, e);
        }
    }

    protected String decodeParameter(String parameter) {
        return new String(parameter.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
