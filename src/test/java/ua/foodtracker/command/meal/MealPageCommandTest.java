package ua.foodtracker.command.meal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.meal.MealPageCommand;
import ua.foodtracker.service.MealService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
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
public class MealPageCommandTest {
    private static final String  CURRENT_PAGE = "1";
    public static final long PAGE_COUNT = 2;

    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private MealService service;

    @InjectMocks
    private MealPageCommand pageCommand;

    @Test
    public void executeShouldReturnUrlSuccessfully(){
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter("page")).thenReturn(CURRENT_PAGE);
        when(service.findAllByPage(CURRENT_PAGE)).thenReturn(Collections.emptyList());
        when(service.pageCount()).thenReturn(PAGE_COUNT);
        doNothing().when(request).setAttribute(eq("meals"),eq(Collections.EMPTY_LIST));
        doNothing().when(request).setAttribute(eq("pageCount"),eq(PAGE_COUNT));

        String url = pageCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.MealService"));
        verify(request).getSession(false);
        verify(session).setAttribute(eq("page"),eq(1));
        verify(service).findAllByPage(CURRENT_PAGE);
        verify(service,times(2)).pageCount();
        verify(request,times(4)).setAttribute(anyString(),any());
    }

    @Test
    public void executeShouldReturnUrlSuccessfullyCase2(){
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.MealService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter("page")).thenReturn(CURRENT_PAGE);
        when(service.findAllByPage(CURRENT_PAGE)).thenReturn(Collections.emptyList());
        when(service.pageCount()).thenReturn(0L);
        doNothing().when(request).setAttribute(eq("meals"),eq(Collections.EMPTY_LIST));

        String url = pageCommand.execute(request);

        assertNotNull(url);
        verify(request).getServletContext();
        verify(context).getAttribute(eq("ua.foodtracker.service.MealService"));
        verify(request).getSession(false);
        verify(session).setAttribute(eq("page"),eq(1));
        verify(service).findAllByPage(CURRENT_PAGE);
        verify(service,times(2)).pageCount();
        verify(request,times(3)).setAttribute(anyString(),any());
    }
}
