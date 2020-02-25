package ua.foodtracker.command.impl.user;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.User;

import javax.servlet.http.HttpServletRequest;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/profile-modify"})
public class ModifyProfileCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User currentUser = getUser(request);
        User modifiedUser = User.builder()
                .withEmail(currentUser.getEmail())
                .withPassword(currentUser.getPassword())
                .withFirstName(decodeParameter(request.getParameter("first_name")))
                .withLastName(decodeParameter(request.getParameter("last_name")))
                .withHeight(getIntParamOrDefault(request, "height", 0))
                .withWeight(getIntParamOrDefault(request, "weight", 0))
                .withLifestyle(Lifestyle.valueOf(request.getParameter("lifestyle")))
                .withBirthday(getDateParamOrNow(request, "birthday"))
                .withGender(Gender.valueOf(request.getParameter("gender")))
                .withRole(currentUser.getRole())
                .withId(currentUser.getId())
                .withUserGoal(currentUser.getUserGoal())
                .build();
        getUserService(request).modify(modifiedUser);
        return "/user/home";
    }
}
