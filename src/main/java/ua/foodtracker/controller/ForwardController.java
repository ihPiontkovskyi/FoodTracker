package ua.foodtracker.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/home", "/error", "/user/records",
        "/user/meals", "/user/meal-add", "/user/meal-edit",
        "/login-page", "/register-page", "/user/profile"})
public class ForwardController extends AbstractController {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(urlToCommand.get(req.getRequestURI()).execute(req)).forward(req, resp);
    }
}
