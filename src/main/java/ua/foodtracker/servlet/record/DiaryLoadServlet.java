package ua.foodtracker.servlet.record;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ua.foodtracker.constant.Constants;
import ua.foodtracker.dto.DailySumsTransferObject;
import ua.foodtracker.entity.Record;
import ua.foodtracker.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@WebServlet(Constants.URI.DIARY_RECORD_URI)
public class DiaryLoadServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Record> dailyRecords = getDiaryRecordService().getPage(getUserId(request), Date.valueOf(getStringParam(request, Constants.Parameters.RECORD_DATE)));
        JsonArray result = new JsonArray();
        result.add(DailySumsTransferObject.build(dailyRecords).toJson());
        for (Record record : dailyRecords) {
            result.add(mapRecordToJson(record));
        }
        PrintWriter out = response.getWriter();
        out.write(result.toString());
        out.flush();
    }

    private JsonObject mapRecordToJson(Record record) {
        JsonObject result = new JsonObject();
        result.add("recordId", new JsonPrimitive(record.getId()));
        result.add("carbohydrate", new JsonPrimitive(record.getMeal().getCarbohydrate()));
        result.add("fat", new JsonPrimitive(record.getMeal().getFat()));
        result.add("protein", new JsonPrimitive(record.getMeal().getProtein()));
        result.add("name", new JsonPrimitive(record.getMeal().getName()));
        result.add("energy", new JsonPrimitive(record.getMeal().calculateEnergy()));
        result.add("water", new JsonPrimitive(record.getMeal().getWater()));
        result.add("weight", new JsonPrimitive(record.getMeal().getWeight()));
        return result;
    }
}


