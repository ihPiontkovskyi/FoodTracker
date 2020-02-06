package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.RegisterCommand;
import ua.foodtracker.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private UserService service;

    @InjectMocks
    private RegisterCommand registerCommand;

    @Test
    public void executeRegisterCommandShouldReturnUrlSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.UserService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(Locale.getDefault());
        when(request.getParameter(anyString())).thenReturn("string");
        doNothing().when(service).register(any());

        String url = registerCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.UserService"));
        verify(request).getSession(false);
        verify(context).getAttribute(any());
        verify(session).getAttribute("locale");
        verify(request, times(9)).getParameter(anyString());
    }
}
