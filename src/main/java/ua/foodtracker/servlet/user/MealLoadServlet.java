package ua.foodtracker.servlet.user;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.entity.User;
import ua.foodtracker.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URL.MEAL_PAGE_URL)
public class MealLoadServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute(Constants.Attributes.CURRENT_USER);
        request.setAttribute("meals", getMealService().findAllByPage(getStringParam(request,"page"), user.getId()));
        switch (user.getRole()) {
            case USER:
                forward("/pages/user/meal.jsp", request, response);
                break;
            case ADMIN:
                forward("/pages/admin/meal.jsp", request, response);
                break;
        }
    }
}