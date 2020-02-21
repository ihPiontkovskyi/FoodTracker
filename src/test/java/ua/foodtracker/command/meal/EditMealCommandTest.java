package ua.foodtracker.command.meal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.EditMealCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.domain.UserGoal;
import ua.foodtracker.exception.AccessDeniedException;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditMealCommandTest {

    private static final User USER = initUser();
    private static final User ADMIN = initAdmin();
    private static final Meal MEAL = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .withUser(USER)
            .build();

    private static final Meal MEAL_ADMIN = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .withUser(ADMIN)
            .build();

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private MealService service;
    @Mock
    private HttpSession session;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private EditMealCommand editMealCommand;

    @Test
    public void testExecuteShouldPass() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(ADMIN);
        when(request.getParameter(anyString())).thenReturn("1");

        assertThat(editMealCommand.execute(request), equalTo("/user/meals"));

        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request).getSession(false);
        verify(service).add(any(Meal.class));
        verify(request, times(7)).getParameter(anyString());
    }

    @Test
    public void testExecuteShouldPassCase2() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(USER);
        when(request.getParameter(anyString())).thenReturn("1");
        when(service.findById("1")).thenReturn(MEAL);

        assertThat(editMealCommand.execute(request), equalTo("/user/meals"));

        verify(request, times(2)).getServletContext();
        verify(context, times(2)).getAttribute(anyString());
        verify(request, times(2)).getSession(false);
        verify(service).add(any(Meal.class));
        verify(service).findById("1");
        verify(request, times(8)).getParameter(anyString());
    }

    @Test
    public void testExecuteShouldThrowAccessDeniedException() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(USER);
        when(request.getParameter(anyString())).thenReturn("1");
        when(service.findById("1")).thenReturn(MEAL_ADMIN);

        exception.expect(AccessDeniedException.class);
        assertThat(editMealCommand.execute(request), equalTo("/user/meals"));

        verify(request, times(2)).getServletContext();
        verify(context, times(2)).getAttribute(anyString());
        verify(request, times(2)).getSession(false);
        verify(service).findById("1");
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

    private static User initAdmin() {
        return User.builder()
                .withId(2)
                .withGender(Gender.FEMALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.ADMIN)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusDays(80))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .build();
    }
}