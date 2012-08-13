package de.lemo.dms.processing.parameter;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement
public abstract class Item<T> extends MetaParam<T> {

    @XmlElement
    private List<T> validValues;

    protected Item() {
        /* JAXB no-arg default constructor */
    }

    public Item(Class<T> type, String id, String name, String description, T defaultValue) {
        super(type, id, name, description, defaultValue);
        validValues = getValidValues();
    }

    protected abstract List<T> getValidValues();

    @Override
    public boolean validate(T argument) {
        return validValues.contains(argument);
    }

}
