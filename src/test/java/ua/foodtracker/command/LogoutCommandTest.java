package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.LogoutCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private LogoutCommand logoutCommand;

    @Test
    public void executeLogoutCommandShouldReturnPathSuccessfully() {
        when(request.getSession(false)).thenReturn(session);
        doNothing().when(session).invalidate();

        String url = logoutCommand.execute(request);

        assertNotNull(url);
        verify(request).getSession(false);
        verify(session).invalidate();
    }
}
