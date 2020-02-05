package ua.foodtracker.command;

import org.apache.log4j.Logger;
import ua.foodtracker.entity.User;
import ua.foodtracker.service.MealService;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
