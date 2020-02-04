package ua.foodtracker.service.utility;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ServiceUtility {
    private static final Logger LOGGER = Logger.getLogger(ServiceUtility.class);

    private static final String ERROR_MESSAGE = "Page number is not valid!";
    public static final int DEFAULT_PAGE = 1;
    public static final String ERROR_RESOURCES_FILENAME = "locale/validate_messages";

    private ServiceUtility() {
    }

    public static long getNumberOfPage(long countOfRecords, long itemsPerPage) {
        return countOfRecords % itemsPerPage == 0 ? countOfRecords / itemsPerPage : countOfRecords / itemsPerPage + 1;
    }

    public static Integer getPageNumberByString(String pageNumber, long numberOfPage) {
        if (pageNumber == null) {
            return DEFAULT_PAGE;
        }
        int page = convertStringToInt(pageNumber);
        if (page > numberOfPage || page <= 0) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    public static String getErrorMessageByIssues(Map<String, String> errorKeyToErrorCause, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(ERROR_RESOURCES_FILENAME, locale);
        LOGGER.trace("Errors found while processing operation");
        StringBuilder error = new StringBuilder();
        errorKeyToErrorCause.forEach((key, value) -> error.append(bundle.getString(value)).append(" "));
        return error.toString();
    }

    private static Integer convertStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            LOGGER.warn(ERROR_MESSAGE);
            return DEFAULT_PAGE;
        }
    }
}
