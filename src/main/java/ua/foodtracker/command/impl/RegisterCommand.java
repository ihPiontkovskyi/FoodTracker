package ua.foodtracker.command.impl;

import ua.foodtracker.command.Command;
import ua.foodtracker.dao.entity.Gender;
import ua.foodtracker.dao.entity.Lifestyle;
import ua.foodtracker.dao.entity.Role;
import ua.foodtracker.raw.type.entity.RawUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public class RegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        RawUser newUser = RawUser.builder()
                .withEmail(request.getParameter("email"))
                .withPassword(request.getParameter("pass"))
                .withFirstName(request.getParameter("first_name"))
                .withLastName(request.getParameter("last_name"))
                .withHeight(getIntParamOrDefault(request, "height", 0))
                .withWeight(getIntParamOrDefault(request, "weight", 0))
                .withLifestyle(Lifestyle.getLifestyleById(getIntParam(request, "lifestyle")))
                .withBirthday(LocalDate.parse(request.getParameter("birthday")))
                .withGender(Gender.getGenderById(getIntParam(request, "gender")))
                .withRole(Role.USER)
                .build();
        getUserService(request).register(newUser);
        return "/pages/login.jsp";
    }
}
