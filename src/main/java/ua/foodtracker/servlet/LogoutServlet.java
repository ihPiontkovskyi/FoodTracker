package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URL.LOGOUT_URL)
public class LogoutServlet extends AbstractServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        unsetCurrentUser(request);
        redirectTo(Constants.Pages.LOGIN_PAGE, request, response);
    }
}