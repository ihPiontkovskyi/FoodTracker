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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private LocaleFilter filter;

    @Test
    public void filterShouldSetLocaleToDefault() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn(null);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(request).getParameter("lang");
        verify(request).getSession(false);
        verify(session).getAttribute("locale");
        verify(session).setAttribute("locale", "en");
    }

    @Test
    public void filterShouldSetLocaleToString() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn("ru");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(request).getParameter("lang");
        verify(request).getSession(false);
        verify(session).getAttribute("locale");
        verify(session).setAttribute("locale", "ru");
    }

    @Test
    public void filterShouldntChangeLocale() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn(null);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn("ru");

        filter.doFilter(request, response, chain);

        verify(request).getParameter("lang");
        verify(request).getSession(false);
        verify(session).getAttribute("locale");
        verify(session,times(0)).setAttribute(eq("locale"), anyString());
    }

    @Test
    public void filterShouldntChangeLocaleCase2() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn("ru");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn("ru");

        filter.doFilter(request, response, chain);

        verify(request).getParameter("lang");
        verify(request).getSession(false);
        verify(session).getAttribute("locale");
        verify(session,times(0)).setAttribute(eq("locale"), anyString());
    }

    @Test
    public void filterShouldChangeLocale() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn("ru");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn("en");

        filter.doFilter(request, response, chain);

        verify(request).getParameter("lang");
        verify(request).getSession(false);
        verify(session).getAttribute("locale");
        verify(session).setAttribute("locale", "ru");
    }

}
