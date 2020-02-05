package ua.foodtracker.service.utility;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ServiceUtility {
    private static final Logger LOGGER = Logger.getLogger(ServiceUtility.class);

    private static final String ERROR_RESOURCES_FILENAME = "locale/validate_messages";

    private ServiceUtility() {
    }

    public static long getNumberOfPage(long countOfRecords, long itemsPerPage) {
        return countOfRecords % itemsPerPage == 0 ? countOfRecords / itemsPerPage : countOfRecords / itemsPerPage + 1;
    }

    public static String getErrorMessageByIssues(Map<String, String> errorKeyToErrorCause, Locale locale) {
        ResourceBundle bundle;
        if (locale != null) {
            bundle = ResourceBundle.getBundle(ERROR_RESOURCES_FILENAME, locale);
        } else {
            bundle = ResourceBundle.getBundle(ERROR_RESOURCES_FILENAME, Locale.getDefault());
        }
        LOGGER.trace("Errors found while processing operation");
        StringBuilder error = new StringBuilder();
        errorKeyToErrorCause.forEach((key, value) -> error.append(bundle.getString(value)).append(" "));
        return error.toString();
    }
}
