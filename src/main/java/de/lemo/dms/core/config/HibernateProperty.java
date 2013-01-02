package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
public class HibernateProperty {

    @XmlAttribute(required = true)
    protected String name;

    @XmlValue
    protected String value;

}
