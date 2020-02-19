package ua.foodtracker.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionFilterTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private SessionFilter filter;

    @Test
    public void filterShouldSetSession() throws IOException, ServletException {
        when(request.getSession(false)).thenReturn(null);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        filter.doFilter(request, response, chain);

        verify(request).getSession(false);
        verify(request).getSession(true);
        verify(session, times(3)).setAttribute(anyString(), any());
    }

    @Test
    public void filterShouldDoNothing() throws IOException, ServletException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);

        filter.doFilter(request, response, chain);

        verify(request).getSession(false);
        verifyNoInteractions(session);
    }
}
