package ua.foodtracker.command.meal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.AddMealCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddMealCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private MealService service;

    @InjectMocks
    private AddMealCommand addMealCommand;

    @Test
    public void testExecuteShouldPassSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter(anyString())).thenReturn("1");

        String url = addMealCommand.execute(request);


        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request).getSession(false);
        verify(request, times(6)).getParameter(anyString());
        verify(service).add(any(Meal.class));
    }
}
