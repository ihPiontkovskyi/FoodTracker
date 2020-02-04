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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebFilter(urlPatterns = {"/pages/user/records"})
public class RecordDateFilter implements Filter {
    public static final String CURRENT_DATE = "currentDate";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LocalDate date = parseDateOrDateNow(req.getParameter("date"));
        Object currentDate = req.getSession(false).getAttribute(CURRENT_DATE);
        if (currentDate == null) {
            req.getSession(false).setAttribute(CURRENT_DATE, LocalDate.now());
        } else {
            if (date != null && !currentDate.equals(date)) {
                req.getSession(false).setAttribute(CURRENT_DATE, date);
            }
        }
        filterChain.doFilter(req, res);
    }

    private LocalDate parseDateOrDateNow(String localDate) {
        if (localDate == null) {
            return null;
        }
        try {
            return LocalDate.parse(localDate);
        } catch (DateTimeParseException ex) {
            return LocalDate.now();
        }
    }
}
