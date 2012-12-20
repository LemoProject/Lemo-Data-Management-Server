package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Connector {

    @XmlAttribute(required = true)
    String name;

    @XmlAttribute(required = true)
    String platform;

    @XmlElement(name = "session-factory")
    List<HibernateProperty> hibernateConfig;

    @XmlElement(name = "log-path")
    String logPath;

    @XmlElement(name = "metadata-path")
    String metaDataPath;
}
