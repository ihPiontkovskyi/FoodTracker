package ua.foodtracker.constant;

public final class Constants {

    private Constants() {
    }

    public static final class Attributes {
        private Attributes() {
        }

        public static final String USER_SERVICE = "ua.foodtracker.service.UserService";
        public static final String DIARY_RECORD_SERVICE = "ua.foodtracker.service.RecordService";
        public static final String MEAL_SERVICE = "ua.foodtracker.service.MealService";
        public static final String CURRENT_USER = "currentUser";
        public static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";
        public static final String ERROR_MESSAGE = "javax.servlet.error.message";
        public static final String LANG = "lang";
        public static final String DEFAULT_LOCALE = "en";
        public static final String LOCALE = "locale";
    }

    public static final class Parameters {
        private Parameters() {
        }

        public static final String USERNAME = "username";
        public static final String PASSWORD = "pass";
        public static final String EMAIL = "email";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String WEIGHT = "weight";
        public static final String HEIGHT = "height";
        public static final String LIFESTYLE_ID = "lifestyle";
        public static final String DATE_OF_BIRTH = "birthday";
        public static final String GENDER = "gender";
        public static final String RECORD_ID = "recordId";
        public static final String RECORD_DATE = "recordsByDate";
        public static final String MEAL_ID = "mealId";
        public static final String MEAL_LIST = "meals";
        public static final String HOME_MODEL = "homeModel";
        public static final String CAUSE = "cause";
    }

    public static final class Pages {
        private Pages() {
        }

        public static final String HOME_PAGE_REQUSET = "/pages/user/home";
        public static final String MEAL_PAGE = "meal.jsp";
        public static final String HOME_PAGE = "home.jsp";
        public static final String USER_SETTINGS_PAGE = "userSettings.jsp";
        public static final String LOGIN_PAGE = "/pages/login.jsp";
        public static final String ERROR_PAGE = "/pages/error.jsp";
    }

    public static final class URL {
        private URL() {
        }

        public static final String LOGIN_URL = "/pages/login";
        public static final String LOGOUT_URL = "/pages/user/logout";
        public static final String REGISTER_URL = "/pages/register";
        public static final String HOME_URL = "/pages/user/home";
        public static final String UPDATE_USER_URL = "/pages/user/updateUser";
        public static final String DELETE_USER_URL = "/pages/user/deleteUser";
        public static final String USER_SETTINGS_URL = "/pages/user/settings";
        public static final String DIARY_RECORD_URL = "/pages/user/getRecordsByDate";
        public static final String DELETE_DIARY_RECORD_URL = "/pages/user/deleteRecord";
        public static final String MEAL_PAGE_URL = "/pages/user/meal";
        public static final String ERROR_URL = "/pages/error";
    }

    public static final class Error {
        private Error() {
        }

        public static final String USER_PROCESSING_CAUSE = "Unable to process user, user doesnt exists or is not available!";
        public static final String RECORD_PROCESSING_CAUSE = "Unable to process record, record doesnt exists or is not available!";
        public static final String LOGIN_PROCESSING_CAUSE = "Incorrect username or password!";
        public static final String DATA_PROCESSING_CAUSE = "Data processing is not available!";
        public static final String USER_IS_UNAVAILABLE = "User is unavailable";
    }
}
