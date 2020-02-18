package ua.foodtracker.command.impl;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (throwable != null) {
            request.setAttribute("cause", throwable.getMessage());
        } else {
            request.setAttribute("cause", request.getAttribute("javax.servlet.error.message"));
        }

        return "/WEB-INF/pages/error.jsp";
    }
}
