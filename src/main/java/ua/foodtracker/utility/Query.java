package ua.foodtracker.utility;

import org.apache.log4j.Logger;
import ua.foodtracker.exception.FileProcessingException;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class, contains all queries used in dao and stored in specified properties file.
 */
public class Query {
    private static final Logger LOGGER = Logger.getLogger(Query.class);

    private static final ResourceBundle QUERIES;
    private static final String SQL_FILE = "properties/query";
    private static final String ERROR_MESSAGE = "Cannot load file: '" + SQL_FILE + "'";

    private Query() {
    }

    static {
        try {
            QUERIES = ResourceBundle.getBundle(SQL_FILE);
        } catch (NullPointerException | MissingResourceException ex) {
            LOGGER.error(ERROR_MESSAGE, ex);
            throw new FileProcessingException(ERROR_MESSAGE, ex);
        }
    }

    /**
     * Gets a query by {@code key}.
     *
     * @param key key of the query to find
     * @return found query
     */
    public static String getQuery(String key) {
        return QUERIES.getString(key);
    }
}
