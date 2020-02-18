package ua.foodtracker.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register", "/user/logout",
        "/user/records/delete", "/user/meals/delete", "/user/records/add",
        "/user/meals/add-meal", "/user/meals/edit-meal", "/user/profile-modify"})
public class RedirectController extends AbstractController {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + urlToCommand.get(req.getRequestURI()).execute(req));
    }
}
