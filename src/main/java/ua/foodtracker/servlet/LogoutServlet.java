package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URI.LOGOUT_URI)
public class LogoutServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        unsetCurrentUser(request);
        redirectTo(Constants.Pages.LOGIN_PAGE, request, response);
    }
}