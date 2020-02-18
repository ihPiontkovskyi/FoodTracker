package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.HomePageCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.domain.UserGoal;
import ua.foodtracker.service.RecordService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePageCommandTest {
    private static final User USER = initUser();

    @Mock
    private HttpServletRequest request;
    @Mock

    private HttpSession session;
    @Mock
    private RecordService recordService;
    @Mock
    private ServletContext context;
    @InjectMocks
    private HomePageCommand homeCommand;

    @Test
    public void executeShouldReturnUrlSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.RecordService"))).thenReturn(recordService);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(eq("user"))).thenReturn(USER);
        doNothing().when(request).setAttribute(eq("homeModel"), any());

        String url = homeCommand.execute(request);

        assertNotNull(url);
        verify(context).getAttribute(anyString());
        verify(request).getServletContext();
        verify(request).getSession(false);
        verify(session).getAttribute(anyString());
        verify(request).setAttribute(eq("homeModel"), any());
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
                .withUserGoal(
                        UserGoal.builder()
                                .withDailyCarbohydrateGoal(1)
                                .withDailyEnergyGoal(1)
                                .withDailyFatGoal(1)
                                .withDailyProteinGoal(1)
                                .withDailyWaterGoal(1)
                                .withId(1).build()
                )
                .build();
    }
}
