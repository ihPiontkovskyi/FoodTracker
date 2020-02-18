package ua.foodtracker.validator;

public interface Validator<E> {

    void validate(E entity);
}
