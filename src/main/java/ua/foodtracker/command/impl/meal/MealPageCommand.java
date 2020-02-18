package ua.foodtracker.command.impl.meal;

import ua.foodtracker.annotation.CommandMapping;
import ua.foodtracker.command.Command;
import ua.foodtracker.command.impl.AbstractCommand;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.service.MealService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@CommandMapping(urlPatterns = {"/foodtracker.ua/user/meals"})
public class MealPageCommand extends AbstractCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer page = getIntParamOrDefault(request, "page", 1);
        session.setAttribute("page", page);
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
        return "/WEB-INF/pages/user/meals.jsp";
    }
}
