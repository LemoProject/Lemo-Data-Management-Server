package de.lemo.dms.processing.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class ParameterMetaData<T> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private T defaultValue;

    private Class<?> type;

    public abstract boolean validate(T argument);

    protected ParameterMetaData() {
        /* JAXB no-arg default constructor */
    }

    protected ParameterMetaData(Class<T> type, String id, String name, String description, T defaultValue) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }

    @XmlElement
    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
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

}