package ua.foodtracker.servlet.user;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.User;
import ua.foodtracker.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(Constants.URI.USER_SETTINGS_URI)
public class UserSettingsPageServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = getUserService().findById(getUserId(request));
        if (user.isPresent()) {
            User currentUser = user.get();
            request.setAttribute(Constants.Parameters.FIRST_NAME, currentUser.getFirstName());
            request.setAttribute(Constants.Parameters.LAST_NAME, currentUser.getLastName());
            request.setAttribute(Constants.Parameters.USERNAME, currentUser.getEmail());
            request.setAttribute(Constants.Parameters.DATE_OF_BIRTH, currentUser.getBirthday());
            request.setAttribute(Constants.Parameters.HEIGHT, currentUser.getHeight());
            request.setAttribute(Constants.Parameters.WEIGHT, currentUser.getWeight());
            request.setAttribute(Constants.Parameters.GENDER, currentUser.getGender().getId());
            request.setAttribute(Constants.Parameters.LIFESTYLE_ID, currentUser.getLifestyle().getId());
            forward(Constants.Pages.USER_SETTINGS_PAGE, request, response);
        } else {
            response.sendError(403, Constants.Error.USER_PROCESSING_CAUSE);
        }
    }
}
