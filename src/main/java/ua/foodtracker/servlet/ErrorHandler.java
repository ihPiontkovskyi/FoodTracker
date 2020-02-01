package ua.foodtracker.servlet;

import ua.foodtracker.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URI.ERROR_URI)
public class ErrorHandler extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable)
                request.getAttribute(Constants.Attributes.SERVLET_EXCEPTION);
        if (throwable != null) {
            request.setAttribute(Constants.Parameters.CAUSE, throwable.getMessage());
        } else {
            request.setAttribute(Constants.Parameters.CAUSE, request.getAttribute(Constants.Attributes.ERROR_MESSAGE));
        }
        forward(Constants.Pages.ERROR_PAGE, request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
