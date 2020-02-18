package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.MealDeleteCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealDeleteCommandTest {
    private static final User USER = initUser(1);
    private static final User USER_WITH_ID_2 = initUser(2);
    private static final User ADMIN = initAdmin();
    private static final Meal MEAL = initMeal();
    private static final java.lang.String ID = "1";

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private MealService service;

    @InjectMocks
    private MealDeleteCommand mealDeleteCommand;

    @Test
    public void executeLoginCommandShouldReturnPathSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(ADMIN);
        when(request.getParameter("id")).thenReturn(ID);

        String url = mealDeleteCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request).getSession(false);
        verify(service).delete(ID);
        verify(request).getParameter(anyString());
    }

    @Test
    public void executeLoginCommandShouldReturnPathSuccessfullyCase2() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(USER);
        when(request.getParameter("id")).thenReturn(ID);
        when(service.findById(ID)).thenReturn(MEAL);

        String url = mealDeleteCommand.execute(request);

        assertNotNull(url);
        verify(request, times(2)).getServletContext();
        verify(context, times(2)).getAttribute(anyString());
        verify(request, times(2)).getSession(false);
        verify(service).findById(ID);
        verify(service).delete(ID);
        verify(request, times(2)).getParameter(anyString());
    }

    @Test
    public void executeLoginCommandShouldReturnPathSuccessfullyCase4() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(USER_WITH_ID_2);
        when(request.getParameter("id")).thenReturn(ID);
        when(service.findById(ID)).thenReturn(MEAL);

        String url = mealDeleteCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request, times(2)).getSession(false);
        verify(service).findById(ID);
        verify(request).getParameter(anyString());
    }


    private static User initUser(Integer id) {
        return User.builder()
                .withId(id)
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

    private static User initAdmin() {
        return User.builder()
                .withId(2)
                .withGender(Gender.MALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.ADMIN)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusYears(30))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .build();
    }

    private static Meal initMeal() {
        return Meal.builder()
                .withCarbohydrates(1)
                .withFat(1)
                .withId(1)
                .withName("name")
                .withProtein(1)
                .withUser(USER)
                .withWater(1)
                .withWeight(1)
                .build();
    }
}
