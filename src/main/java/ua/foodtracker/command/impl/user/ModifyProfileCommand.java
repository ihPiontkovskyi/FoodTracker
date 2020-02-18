package ua.foodtracker.command.impl.user;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

public class ModifyProfileCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        User currentUser = getUser(request);
        User modifiedUser = User.builder()
                .withEmail(currentUser.getEmail())
                .withPassword(currentUser.getPassword())
                .withFirstName(request.getParameter("first_name"))
                .withLastName(request.getParameter("last_name"))
                .withHeight(getIntParamOrDefault(request, "height", 0))
                .withWeight(getIntParamOrDefault(request, "weight", 0))
                .withLifestyle(Lifestyle.valueOf(request.getParameter("lifestyle")))
                .withBirthday(getDateParamOrNow(request, "birthday"))
                .withGender(Gender.valueOf(request.getParameter("gender")))
                .withRole(currentUser.getRole())
                .build();
        getUserService(request).modify(modifiedUser);
        return "/user/home";
    }
}
