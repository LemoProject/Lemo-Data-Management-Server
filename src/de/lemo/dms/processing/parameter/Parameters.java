package de.lemo.dms.processing.parameter;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for a list of {@link Parameter} for JAXB list conversion.
 * 
 * @author Leonard Kappe
 */
@XmlRootElement
public class Parameters {

    @XmlElement
    private List<Parameter<?>> parameters;

    /* JAXB required no-arg constructor */
    public Parameters() {
    }

    public Parameters(List<Parameter<?>> parameters) {
        this.parameters = parameters;
    }

    public List<Parameter<?>> getParameters() {
        return parameters;
    }

}
