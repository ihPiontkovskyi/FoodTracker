package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.User;
import ua.foodtracker.validator.Validator;
import ua.foodtracker.validator.impl.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

@WebServlet(Constants.URI.REGISTER_URI)
public class RegisterServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, java.io.IOException {
        User newUser = User.builder()
                .withEmail(getStringParam(request, Constants.Parameters.EMAIL))
                .withPassword(getStringParam(request, Constants.Parameters.PASSWORD))
                .withFirstName(getStringParam(request, Constants.Parameters.FIRST_NAME))
                .withLastName(getStringParam(request, Constants.Parameters.LAST_NAME))
                .withHeight(getIntParam(request, Constants.Parameters.HEIGHT))
                .withWeight(getIntParam(request, Constants.Parameters.WEIGHT))
                .withLifestyle(Lifestyle.getLifestyleById(getIntParam(request, Constants.Parameters.LIFESTYLE_ID)))
                .withBirthday(Date.valueOf(getStringParam(request, Constants.Parameters.DATE_OF_BIRTH)))
                .withGender(Gender.getGenderById(getIntParam(request, Constants.Parameters.GENDER)))
                .withRole(Role.USER)
                .build();
        Validator validator = new UserValidator(newUser, request.getLocale());
        if (!validator.hasErrors()) {
            if (getUserService().register(newUser)) {
                redirectTo(Constants.Pages.LOGIN_PAGE, request, response);
            } else {
                response.sendError(403, Constants.Error.DATA_PROCESSING_CAUSE);
            }
        }
    }
}
