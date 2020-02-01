package ua.foodtracker.servlet.user;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.User;
import ua.foodtracker.servlet.AbstractServlet;
import ua.foodtracker.validator.Validator;
import ua.foodtracker.validator.impl.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

@WebServlet(Constants.URI.UPDATE_USER_URI)
public class UpdateUserServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = getUserService().findById(getUserId(request));
        if (user.isPresent()) {
            User currentUser = user.get();
            User userForUpdate = User.builder()
                    .withId(currentUser.getId())
                    .withEmail(currentUser.getEmail())
                    .withPassword(currentUser.getPassword())
                    .withFirstName(getStringParam(request, Constants.Parameters.FIRST_NAME))
                    .withLastName(getStringParam(request, Constants.Parameters.LAST_NAME))
                    .withHeight(getIntParam(request, Constants.Parameters.HEIGHT))
                    .withWeight(getIntParam(request, Constants.Parameters.WEIGHT))
                    .withBirthday(Date.valueOf(getStringParam(request, Constants.Parameters.DATE_OF_BIRTH)))
                    .withGender(Gender.getGenderById(getIntParam(request, Constants.Parameters.GENDER)))
                    .withLifestyle(Lifestyle.getLifestyleById(getIntParam(request, Constants.Parameters.LIFESTYLE_ID)))
                    .withRole(currentUser.getRole())
                    .build();
            Validator validator = new UserValidator(userForUpdate, request.getLocale());
            if (!validator.hasErrors()) {
                if (getUserService().modify(userForUpdate)) {
                    redirectTo(Constants.Pages.HOME_PAGE_REQUSET, request, response);
                } else {
                    response.sendError(406, Constants.Error.USER_PROCESSING_CAUSE);
                }
            }
        } else {
            response.sendError(401, Constants.Error.USER_IS_UNAVAILABLE);
        }
    }
}
