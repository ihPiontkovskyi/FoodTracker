package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.LoginCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.entity.GenderEntity;
import ua.foodtracker.entity.LifestyleEntity;
import ua.foodtracker.entity.RoleEntity;
import ua.foodtracker.service.UserService;
import ua.foodtracker.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {

    private static final User USER = initUser();

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private UserService service;

    @InjectMocks
    private LoginCommand loginCommand;

    @Test
    public void executeLoginCommandShouldReturnPathSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.UserService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter("username")).thenReturn(USER.getEmail());
        when(request.getParameter("pass")).thenReturn(USER.getPassword());
        when(service.login(USER.getEmail(), USER.getPassword())).thenReturn(USER);
        doNothing().when(session).setAttribute("user", USER);

        String url = loginCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.UserService"));
        verify(request).getSession(false);
        verify(context).getAttribute(any());
        verify(request).getParameter("username");
        verify(request).getParameter("pass");
        verify(service).login(USER.getEmail(), USER.getPassword());
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
                .withBirthday(LocalDate.now().minusYears(30))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .build();
    }
}
