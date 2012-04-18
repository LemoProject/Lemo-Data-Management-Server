package de.lemo.dms.processing.parameter;

import javax.ws.rs.QueryParam;
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

    /**
     * Creates a simple parameter, whose type and ID must match of
     * REST-interface method's parameters.
     * 
     * @param type
     *            A type that must be equals the type of the method parameter
     *            with the same ID
     * @param id
     *            An ID that must match a method parameter's {@link QueryParam}
     *            value
     * @param name
     * @param description
     * @param defaultValue
     * @return A new parameter
     */
    public static <T> Parameter<T> create(Class<T> type, String id, String name, String description, T defaultValue) {
        return new Parameter<T>(type, id, name, description, defaultValue);
    }

    public static <T> Parameter<T> create(Class<T> type, String id, String name, String description) {
        return new Parameter<T>(type, id, name, description, null);
    }

    /**
     * Creates a simple parameter, whose type will be the replaced by it's
     * matching method parameter.
     * 
     * @param id
     *            An ID that must match a method parameter's {@link QueryParam}
     *            value
     * @param name
     *            A name or title
     * @param description
     * @return A new parameter
     */
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
