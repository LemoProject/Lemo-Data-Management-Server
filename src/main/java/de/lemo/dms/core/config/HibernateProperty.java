package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
public class HibernateProperty {
    
    @XmlAttribute
    String name;

    @XmlValue
    List<String> value;

}
