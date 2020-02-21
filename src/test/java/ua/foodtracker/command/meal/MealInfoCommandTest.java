package ua.foodtracker.command.meal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.MealsInfoCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealInfoCommandTest {
    private static final Meal MEAL = initMeal();

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private MealService service;

    @InjectMocks
    private MealsInfoCommand mealInfoCommand;

    @Test
    public void testExecuteShouldReturnEmptyJson() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getParameter("term")).thenReturn("string");
        when(service.findAllByNameStartWith("string")).thenReturn(Collections.emptyList());

        String json = mealInfoCommand.execute(request);

        assertThat(json, equalTo("[]"));
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.MealService"));
        verify(request).getParameter("term");
        verify(service).findAllByNameStartWith("string");
    }

    @Test
    public void testExecuteShouldReturnJson() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getParameter("term")).thenReturn("string");
        when(service.findAllByNameStartWith("string")).thenReturn(Collections.singletonList(MEAL));

        String json = mealInfoCommand.execute(request);

        assertThat(json, equalTo("[{\"id\":1,\"label\":\"name\"}]"));
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.MealService"));
        verify(request).getParameter("term");
        verify(service).findAllByNameStartWith("string");
    }


    private static Meal initMeal() {
        return Meal.builder()
                .withCarbohydrates(1)
                .withFat(1)
                .withId(1)
                .withName("name")
                .withProtein(1)
                .withUser(null)
                .withWater(1)
                .withWeight(1)
                .build();
    }
}
