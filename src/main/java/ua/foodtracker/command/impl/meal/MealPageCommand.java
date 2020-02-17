package ua.foodtracker.command.impl.meal;

import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class MealPageCommand extends AbstractCommand {
    @Override
    public String execute(HttpServletRequest request) {
        final MealService mealService = getMealService(request);
        List<Meal> meals = mealService.findAllByPage(request.getParameter("page"));
        long totalPages = mealService.pageCount();
        request.setAttribute("totalPages", totalPages);
        if (totalPages > 0) {
            List<Long> pageNumbers = LongStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            request.setAttribute("pageNumbers", pageNumbers);
        }
        request.setAttribute("meals", meals);
        request.setAttribute("pageCount", mealService.pageCount());
        return "/pages/user/meal/meals.jsp";
    }
}
