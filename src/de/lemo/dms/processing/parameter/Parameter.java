package de.lemo.dms.processing.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An unconstrained parameter, which provides meta information about purpose and
 * type of the parameter's argument.
 * 
 * @author Leonard Kappe
 * 
 * @param <T>
 *            Type of the argument.
 */

@XmlRootElement
public class Parameter<T> extends ParameterMetaData<T> {

    public static <T> Parameter<T> create(Class<T> type, String id, String name, String description) {
        return new Parameter<T>(type, id, name, description, null);
    }

    public static <T> Parameter<T> create(Class<T> type, String id, String name, String description, T defaultValue) {
        return new Parameter<T>(type, id, name, description, defaultValue);
    }

    public static Parameter<Void> create(String id, String name, String description) {
        return new Parameter<Void>(Void.class, id, name, description, null);
    }

    protected Parameter() {
        /* JAXB no-arg default constructor */
    }

    protected Parameter(Class<T> type, String id, String name, String description, T defaultValue) {
        super(type, id, name, description, defaultValue);
    }

    @Override
    public boolean validate(T argument) {
        return true; // no constraints.
    }

}
