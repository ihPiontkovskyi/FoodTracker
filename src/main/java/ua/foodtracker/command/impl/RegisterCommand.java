package ua.foodtracker.command.impl;

import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        User newUser = User.builder()
                .withEmail(request.getParameter("email"))
                .withPassword(request.getParameter("pass"))
                .withFirstName(request.getParameter("first_name"))
                .withLastName(request.getParameter("last_name"))
                .withHeight(getIntParamOrDefault(request, "height", 0))
                .withWeight(getIntParamOrDefault(request, "weight", 0))
                .withLifestyle(Lifestyle.getLifestyleById(getIntParam(request, "lifestyle")))
                .withBirthday(getDateParamOrNow(request, "birthday"))
                .withGender(Gender.getGenderById(getIntParam(request, "gender")))
                .withRole(Role.USER)
                .build();
        getUserService(request).register(newUser);
        return "/pages/login.jsp";
    }
}
