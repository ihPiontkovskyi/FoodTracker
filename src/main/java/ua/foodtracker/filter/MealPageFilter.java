package ua.foodtracker.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class MealPageFilter implements Filter {

    private static final String CURRENT_PAGE = "currentPage";
    private static final Integer DEFAULT_PAGE = 1;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Integer page = parseLongOrDefault(req.getParameter("page"));
        Object currentPage = req.getSession(false).getAttribute(CURRENT_PAGE);
        if (currentPage == null) {
            req.getSession(false).setAttribute(CURRENT_PAGE, DEFAULT_PAGE);
        } else {
            if (page != null && !currentPage.equals(page)) {
                req.getSession(false).setAttribute(CURRENT_PAGE, page);
            }
        }
        filterChain.doFilter(req, res);
    }

    private Integer parseLongOrDefault(String page) {
        if (page == null) {
            return null;
        }
        try {
            return Integer.parseInt(page);
        } catch (DateTimeParseException ex) {
            return DEFAULT_PAGE;
        }
    }
}
