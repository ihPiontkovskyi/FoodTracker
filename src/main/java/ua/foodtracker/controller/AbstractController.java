package ua.foodtracker.controller;

import ua.foodtracker.command.Command;

import javax.servlet.http.HttpServlet;
import java.util.Map;

@SuppressWarnings({"unchecked", "squid:S2226"})
public abstract class AbstractController extends HttpServlet {
    protected Map<String, Command> urlToCommand;

    @Override
    public void init() {
        this.urlToCommand = (Map<String, Command>) getServletContext().getAttribute("urlToCommandMap");
    }
}
