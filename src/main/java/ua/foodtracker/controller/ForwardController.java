package ua.foodtracker.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/pages/user/home", "/pages/error", "/pages/user/records",
        "/pages/user/meals","/pages/user/meals/add","/pages/user/meals/edit"})
public class ForwardController extends AbstractController {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(urlToCommand.get(req.getRequestURI()).execute(req)).forward(req, resp);
    }
}
