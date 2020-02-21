package ua.foodtracker.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.user.ModifyProfileCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.domain.UserGoal;
import ua.foodtracker.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModifyUserProfileCommandTest {

    private static final User USER = initUser();

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private UserService service;
    @Mock
    private HttpSession session;
    @InjectMocks
    private ModifyProfileCommand modifyProfileCommand;

    @Test
    public void executeShouldPassSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.UserService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(USER);
        when(request.getParameter(anyString())).thenReturn("string");
        when(request.getParameter("birthday")).thenReturn(null);
        when(request.getParameter("weight")).thenReturn(null);
        when(request.getParameter("lifestyle")).thenReturn("ACTIVE");
        when(request.getParameter("gender")).thenReturn("MALE");

        assertThat(modifyProfileCommand.execute(request), equalTo("/user/home"));

        verify(context).getAttribute(anyString());
        verify(request).getServletContext();
        verify(request).getSession(false);
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.UserService"));
        verify(context).getAttribute(any());
        verify(request, times(7)).getParameter(anyString());
    }

    private static User initUser() {
        return User.builder()
                .withId(1)
                .withGender(Gender.MALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.USER)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusDays(80))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .withUserGoal(UserGoal.builder()
                        .withId(1)
                        .withDailyCarbohydrateGoal(2)
                        .withDailyEnergyGoal(3)
                        .withDailyFatGoal(1)
                        .withDailyProteinGoal(1)
                        .withDailyWaterGoal(2)
                        .build())
                .build();
    }
}
