package ua.foodtracker.command.meal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.EditMealPageCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditMealPageCommandTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private MealService service;
    @InjectMocks
    private EditMealPageCommand editMealPageCommand;

    @Test
    public void testExecuteShouldPass() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getParameter("id")).thenReturn("1");
        when(service.findById(eq("1"))).thenReturn(Meal.builder().build());

        assertThat(editMealPageCommand.execute(request), equalTo("/WEB-INF/pages/user/edit-meal.jsp"));

        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request).getParameter("id");
        verify(service).findById(anyString());
    }

}

