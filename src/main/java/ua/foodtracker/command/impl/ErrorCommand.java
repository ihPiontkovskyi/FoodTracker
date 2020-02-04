package ua.foodtracker.command.impl;

import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Throwable throwable = (Throwable)
                request.getAttribute("javax.servlet.error.exception");
        if (throwable != null) {
            request.setAttribute("cause", throwable.getMessage());
        } else {
            request.setAttribute("cause", request.getAttribute("javax.servlet.error.message"));
        }
        return "/pages/error.jsp";
    }
}
