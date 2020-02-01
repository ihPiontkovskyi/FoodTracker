package ua.foodtracker.servlet.record;

import ua.foodtracker.constant.Constants;
import ua.foodtracker.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.URI.DELETE_DIARY_RECORD_URI)
public class DiaryRecordDeleteServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!getDiaryRecordService().delete(getIntParam(request, Constants.Parameters.RECORD_ID))) {
            response.sendError(406, Constants.Error.RECORD_PROCESSING_CAUSE);
        }
    }
}
