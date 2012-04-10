package de.lemo.dms.processing.parameter;

/**
 * An abstract parameter, which provides meta information about purpose and type
 * of the parameter's argument. Implementation of argument validation is
 * deferred to a subclass.
 * 
 * @author Leonard Kappe
 * 
 * @param <T>
 *            Type of the argument.
 */
public abstract class Parameter<T> {

    private String name;
    private String description;

    public Parameter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * 
     * @param argument
     *            The argument to validate
     * @return true, if the arguments is valid
     */
    public boolean validateArgument(Object argument) {
        if (argument == null) {
            return false;
        }
        return validate((T) argument);
    }

    protected abstract boolean validate(T argument);
}
