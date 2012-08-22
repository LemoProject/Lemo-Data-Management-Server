package de.lemo.dms.processing.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class MetaParam<T> {

    public static final String COURSE_IDS = "cid";
    public static final String END_TIME = "end";
    public static final String LOG_OBJECT_IDS = "oid";
    public static final String LOGOUT_FLAG = "logout";
    public static final String RESOLUTION = "resolution";
    public static final String ROLE_IDS = "rid";
    public static final String START_TIME = "start";
    public static final String TYPES = "types";
    public static final String USER_IDS = "uid";
    public static final String MIN_SUP = "min_sup";
    public static final String SESSION_WISE = "session_wise";

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

    protected MetaParam() {
        /* JAXB no-arg default constructor */
    }

    protected MetaParam(Class<T> type, String id, String name, String description, T defaultValue) {
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