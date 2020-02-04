package ua.foodtracker.filter;

import ua.foodtracker.entity.Role;
import ua.foodtracker.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User user;
        if (!(session != null && ((user = (User) session.getAttribute("user")) != null))) {
            res.sendRedirect(req.getContextPath() + "/pages/login");
        } else {
            Role userRole = user.getRole();
            if (userRole.equals(Role.USER) && ((HttpServletRequest) request).getContextPath().contains("admin")) {
                res.sendError(401);
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
