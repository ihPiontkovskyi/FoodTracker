package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.LoginPageCommand;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LoginPageCommandTest {
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private LoginPageCommand loginPageCommand;

    @Test
    public void testLoginPage() {
        assertThat(loginPageCommand.execute(request), equalTo("/WEB-INF/pages/login.jsp"));
        verifyNoInteractions(request);
    }
}
