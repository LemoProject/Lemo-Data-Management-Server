package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DataManagementServer {

    @XmlAttribute
    String name;

    @XmlElement(name = "session-factory", required = true)
    List<HibernateProperty> hibernateConfig;

    @XmlElement
    List<Connector> connectors;

}
