package ua.foodtracker.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/pages/login", "/pages/register", "/pages/user/logout", "/pages/user/records/delete", "/pages/user/meals/delete"})
public class RedirectController extends AbstractController {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + urlToCommand.get(req.getRequestURI()).execute(req, resp));
    }
}
