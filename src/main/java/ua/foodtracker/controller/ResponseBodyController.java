package ua.foodtracker.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/byTerm"})
public class ResponseBodyController extends AbstractController {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respBody = req.getContextPath() + urlToCommand.get(req.getRequestURI()).execute(req);
        final PrintWriter writer = resp.getWriter();
        writer.print(respBody);
        writer.flush();
    }
}
