package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.ErrorCommand;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCommandTest {
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ErrorCommand errorCommand;

    @Test
    public void executeErrorCommandShouldReturnPathSuccessfully() {
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(null);
        when(request.getAttribute("javax.servlet.error.message")).thenReturn("message");
        doNothing().when(request).setAttribute("cause", "message");

        String url = errorCommand.execute(request);

        assertNotNull(url);
        verify(request, times(2)).getAttribute(anyString());
        verify(request).setAttribute(eq("cause"), eq("message"));
    }

    @Test
    public void executeErrorCommandShouldReturnPathSuccessfullyCase2() {
        Throwable error = new Throwable("message");
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(error);
        doNothing().when(request).setAttribute("cause", error.getMessage());

        String url = errorCommand.execute(request);

        assertNotNull(url);
        verify(request).getAttribute(anyString());
        verify(request).setAttribute(eq("cause"), eq("message"));
    }
}
