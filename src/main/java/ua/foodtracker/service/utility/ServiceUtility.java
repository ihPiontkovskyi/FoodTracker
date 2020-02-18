package ua.foodtracker.service.utility;

import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.validator.Validator;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class ServiceUtility {

    private static final String INCORRECT_DATA = "incorrect.data";
    private static final String DATA_KEY = "data";

    private ServiceUtility() {
    }

    public static long getNumberOfPage(long countOfRecords, long itemsPerPage) {
        return countOfRecords % itemsPerPage == 0 ? countOfRecords / itemsPerPage : countOfRecords / itemsPerPage + 1;
    }

    public static void deleteByStringId(String id, Validator<?> validator, IntPredicate invoke) {
        if (id == null) {
            throw new IncorrectDataException(INCORRECT_DATA);
        }
        try {
            if (!invoke.test(Integer.parseInt(id))) {
                throw new IncorrectDataException(INCORRECT_DATA);
            }
        } catch (NumberFormatException ex) {
            throw new IncorrectDataException(INCORRECT_DATA);
        }
    }

    public static <T, E> E findByStringParam(String param, Validator<T> validator, IntFunction<E> function) {
        if (param == null) {
            return function.apply(1);
        }
        try {
            return function.apply(Integer.parseInt(param));
        } catch (NumberFormatException ex) {
            throw new IncorrectDataException(INCORRECT_DATA);
        }
    }

    public static <T> void modifyByType(T object, Validator<T> validator, Predicate<T> predicate) {
        validator.validate(object);
        if (!predicate.test(object)) {
            throw new IncorrectDataException(INCORRECT_DATA);
        }
    }

    public static <T> void addByType(T object, Validator<T> validator, ToIntFunction<T> function) {
        validator.validate(object);
        if (function.applyAsInt(object) == 0) {
            throw new IncorrectDataException(INCORRECT_DATA);
        }
    }
}
