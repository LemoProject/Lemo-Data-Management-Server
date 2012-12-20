package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class ApplicationServer {

    @XmlAttribute
    String name;

    @XmlElement(name = "dms-url")
    String dataManagementServerURL;

    @XmlElement(name = "session-factory", required = true)
    List<HibernateProperty> hibernateConfig;
}
