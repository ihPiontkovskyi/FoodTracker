package ua.foodtracker.command.impl;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/error"})
public class ErrorCommand extends AbstractCommand implements Command {
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
