package ua.foodtracker.service.utility;

import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.validator.Validator;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class ServiceUtility {

    private static final String INCORRECT_DATA = "incorrect.data";
    public static final String DATA_KEY = "data";

    private ServiceUtility() {
    }

    public static long getNumberOfPage(long countOfRecords, long itemsPerPage) {
        return countOfRecords % itemsPerPage == 0 ? countOfRecords / itemsPerPage : countOfRecords / itemsPerPage + 1;
    }

    public static void deleteByStringId(String id, Validator<?> validator, IntPredicate invoke) {
        if (id == null) {
            validator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(validator.getErrorMessageByIssues());
        }
        try {
            if (!invoke.test(Integer.parseInt(id))) {
                validator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(validator.getErrorMessageByIssues());
            }
        } catch (NumberFormatException ex) {
            validator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(validator.getErrorMessageByIssues());
        }
    }

    public static <T, E> E findByStringParam(String param, Validator<T> validator, IntFunction<E> function) {
        if (param == null) {
            validator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(validator.getErrorMessageByIssues());
        }
        try {
            return function.apply(Integer.parseInt(param));
        } catch (NumberFormatException ex) {
            validator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(validator.getErrorMessageByIssues());
        }
    }

    public static <T> void modifyByType(T object, Validator<T> validator, Predicate<T> predicate) {
        validator.validate(object);
        if (!validator.hasErrors()) {
            if (!predicate.test(object)) {
                validator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(validator.getErrorMessageByIssues());
            }
        } else {
            throw new ValidationException(validator.getErrorMessageByIssues());
        }
    }

    public static <T> void addByType(T object, Validator<T> validator, ToIntFunction<T> function) {
        validator.validate(object);
        if (!validator.hasErrors()) {
            if (function.applyAsInt(object) == 0) {
                validator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(validator.getErrorMessageByIssues());
            }
        } else {
            throw new ValidationException(validator.getErrorMessageByIssues());
        }
    }
}
