package ua.foodtracker.filter;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LocaleFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class);
    public static final String LOCALE = "locale";
    public static final String DEFAULT_LOCALE = "en";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String locale = req.getParameter("lang");
        Object currentLocale = req.getSession(true).getAttribute(LOCALE);
        if (currentLocale == null && locale == null) {
            LOGGER.info(String.format("Locale filter set locale to %s", DEFAULT_LOCALE));
            req.getSession(false).setAttribute(LOCALE, DEFAULT_LOCALE);
        } else {
            if (currentLocale == null || (locale != null && !currentLocale.toString().equals(locale))) {
                LOGGER.info(String.format("Locale filter set locale to %s", locale));
                req.getSession(false).setAttribute(LOCALE, locale);
            }
        }
        chain.doFilter(req, res);
    }
}
