package de.lemo.dms.processing.parameter;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for a list of {@link Parameter} for JAXB list conversion.
 * Jax-RS limitation don't allow a JSON response object to be a collection,
 * instead a simple root element like this class is required.
 * 
 * @author Leonard Kappe
 */
@XmlRootElement
public class Parameters {

    @XmlElement
    private Collection<ParameterMetaData<?>> parameters;

    public Parameters() {
        /* JAXB no-arg default constructor */
    }

    public Parameters(Collection<ParameterMetaData<?>> parameters) {
        this.parameters = parameters;
    }

    public Collection<ParameterMetaData<?>> getParameters() {
        return parameters;
    }

}
