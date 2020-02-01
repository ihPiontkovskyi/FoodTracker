package ua.foodtracker.filter;

import ua.foodtracker.constant.Constants;

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

/**
 * Filters request and defines whether the current user can access specified path.
 * In case of denied access redirects to the path that can be accessed.
 */

@WebFilter(Constants.URI.AUTHENTICATION_FILTER_URI)
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (!(session != null && (session.getAttribute(Constants.Attributes.CURRENT_USER) != null))) {
            res.sendRedirect(Constants.Pages.LOGIN_PAGE);
        } else {
            chain.doFilter(request, response);        }
    }
}
