package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.dto.HomeModelTransferObject;
import ua.foodtracker.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(Constants.URL.HOME_URL)
public class HomePageLoadServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = getUserService().findById(getUserId(request));
        if (user.isPresent()) {
            request.setAttribute(Constants.Parameters.HOME_MODEL, HomeModelTransferObject.build(user.get(), getDiaryRecordService()));
            forward(Constants.Pages.HOME_PAGE, request, response);
        } else {
            response.sendError(401, Constants.Error.USER_IS_UNAVAILABLE);
        }
    }
}
