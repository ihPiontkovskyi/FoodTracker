package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(Constants.URI.LOGIN_URI)
public class LoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, java.io.IOException {
        User user = getUserService().login(getStringParam(request, Constants.Parameters.USERNAME), getStringParam(request, Constants.Parameters.PASSWORD));
        if (user != null) {
            setCurrentUser(request, user);
            redirectTo(Constants.Pages.HOME_PAGE_REQUSET, request, response);
        } else {
            response.sendError(401, Constants.Error.LOGIN_PROCESSING_CAUSE);
        }
    }
}
