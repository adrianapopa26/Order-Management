package BusinessLogic.Validators;

public interface Validator<T> {
    /**
     * General validator
     * @param t object to be validated
     * @return true or false
     */
    boolean validate(T t);
}