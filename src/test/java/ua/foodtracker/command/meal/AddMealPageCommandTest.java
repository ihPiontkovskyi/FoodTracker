package ua.foodtracker.command.meal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.AddMealPageCommand;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddMealPageCommandTest {
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private AddMealPageCommand addMealPageCommand;

    @Test
    public void testLoginPage() {
        assertThat(addMealPageCommand.execute(request), equalTo("/WEB-INF/pages/user/add-meal.jsp"));
        verifyNoInteractions(request);
    }
}