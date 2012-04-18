package de.lemo.dms.processing.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

@XmlRootElement
public class Parameter<T> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private T defaultValue;

    public Parameter() {
        /* JAXB no-arg default constructor */
    }

    public Parameter(String id, String name, String description, T defaultValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }

    public Parameter(String id, String name, String description) {
        this(id, name, description, null);
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @param argument
     *            The argument to validate
     * @return true, if the argument is valid
     */
    @SuppressWarnings("unchecked")
    public boolean validateArgument(Object argument) {
        if (argument == null) {
            return false;
        }
        return validate((T) argument);
    }

    protected boolean validate(T argument) {
        return true;
    }

}
