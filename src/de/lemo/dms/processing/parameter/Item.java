package de.lemo.dms.processing.parameter;

import java.util.List;

/**
 * A parameter whose argument must be a member of a set. The set's values may be
 * predefined or calculated dynamically.
 * 
 * 
 * @author Leonard Kappe
 * 
 * @param <T>
 *            Type of the argument.
 */
public abstract class Item<T> extends Parameter<T> {

    private List<T> validValues;

    public Item(String name, String description) {
        super(name, description);
        validValues = getValidValues();
    }

    protected boolean validate(T argument) {
        return validValues.contains(argument);
    }

    protected abstract List<T> getValidValues();
}
