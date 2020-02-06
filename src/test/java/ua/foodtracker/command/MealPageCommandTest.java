package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.LoginCommand;
import ua.foodtracker.command.impl.user.MealPageCommand;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Role;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.UserService;
import ua.foodtracker.service.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealPageCommandTest {
    private static final int CURRENT_PAGE = 1;
    private static final User USER = initUser();
    public static final long PAGE_COUNT = 2;

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private MealService service;

    @InjectMocks
    private MealPageCommand pageCommand;

    @Test
    public void executeShouldReturnUrlSuccessfully(){
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentPage")).thenReturn(CURRENT_PAGE);
        when(session.getAttribute("user")).thenReturn(USER);
        when(session.getAttribute("locale")).thenReturn(Locale.getDefault());
        when(service.findAllByPage(CURRENT_PAGE,USER.getId())).thenReturn(Collections.emptyList());
        when(service.pageCount()).thenReturn(PAGE_COUNT);
        doNothing().when(request).setAttribute(eq("meals"),eq(Collections.EMPTY_LIST));
        doNothing().when(request).setAttribute(eq("pageCount"),eq(PAGE_COUNT));

        String url = pageCommand.execute(request);

        assertNotNull(url);
        verify(request,times(2)).getServletContext();
        verify(context, times(2)).getAttribute(eq("ua.foodtracker.service.MealService"));
        verify(request, times(4)).getSession(false);
        verify(session).getAttribute(eq("currentPage"));
        verify(session).getAttribute(eq("user"));
        verify(session, times(2)).getAttribute(eq("locale"));
        verify(service).findAllByPage(CURRENT_PAGE,USER.getId());
        verify(service).pageCount();
        verify(request,times(2)).setAttribute(anyString(),any());
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