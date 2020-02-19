package ua.foodtracker.filter;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class);
    private static final String LOCALE = "locale";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String locale = req.getParameter("lang");
        final HttpSession session = req.getSession(false);
        Object currentLocale = session.getAttribute(LOCALE);
        if (currentLocale == null) {
            if (locale == null) {
                session.setAttribute(LOCALE, "en");
            } else {
                LOGGER.info(String.format("Locale filter set locale to %s", locale));
                session.setAttribute(LOCALE, locale);
            }
        } else if (!currentLocale.toString().equals(locale)) {
            LOGGER.info(String.format("Locale filter set locale to %s", locale));
            session.setAttribute(LOCALE, locale);
        }

        chain.doFilter(req, res);
    }
}
