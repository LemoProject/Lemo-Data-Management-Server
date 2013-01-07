package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
class PropertyConfig {

    @XmlAttribute(name = "name", required = true)
    public String key;

    @XmlValue
    public String value;

}
