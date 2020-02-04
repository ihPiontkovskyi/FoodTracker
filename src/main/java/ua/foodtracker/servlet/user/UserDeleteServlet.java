package ua.foodtracker.servlet.user;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URL.DELETE_USER_URL)
public class UserDeleteServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (getUserService().delete(getUserId(request))) {
            unsetCurrentUser(request);
            redirectTo(Constants.Pages.LOGIN_PAGE, request, response);
        } else {
            response.sendError(406, Constants.Error.USER_PROCESSING_CAUSE);
        }
    }
}