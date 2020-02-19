package ua.foodtracker.command.record;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.record.AddRecordCommand;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddRecordCommandTest {
    private static final User USER = initUser();
    private static final Meal MEAL = initMeal();

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private RecordService service;
    @Mock
    private MealService mealService;

    @InjectMocks
    private AddRecordCommand addRecordCommand;

    @Test
    public void executeShouldReturnUrlSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(mealService);
        when(context.getAttribute(eq("ua.foodtracker.service.RecordService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter("meal_id")).thenReturn("1");
        when(request.getParameter("weight")).thenReturn("100");
        when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
        when(session.getAttribute("user")).thenReturn(USER);
        when(mealService.findById(anyString())).thenReturn(MEAL);

        String url = addRecordCommand.execute(request);

        verify(request, times(2)).getServletContext();
        verify(context, times(2)).getAttribute(anyString());
        verify(request, times(1)).getSession(false);
        verify(session, times(1)).getAttribute(anyString());
        verify(service).add(any());
        verify(mealService).findById(anyString());
        verify(request,times(3)).getParameter(anyString());
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
