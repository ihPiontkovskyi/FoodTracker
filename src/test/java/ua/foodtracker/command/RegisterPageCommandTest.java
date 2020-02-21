package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.LoginPageCommand;
import ua.foodtracker.command.impl.RegisterPageCommand;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPageCommandTest {
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private RegisterPageCommand registerPageCommand;

    @Test
    public void testLoginPage() {
        assertThat(registerPageCommand.execute(request), equalTo("/WEB-INF/pages/register.jsp"));
        verifyNoInteractions(request);
    }
}
