package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType
class DataManagementServer {

    private static final String DEFAULT_NAME = "Lemo Data Management Server";

    @XmlAttribute
    public String name = DEFAULT_NAME;

    @XmlElementWrapper(name = "database", required = true)
    @XmlElement(name = "property")
    public List<PropertyConfig> databaseProperties = Lists.newArrayList();

    @XmlElementWrapper(name = "connectors", required = true)
    @XmlElement(name = "connector")
    public List<Connector> connectors = Lists.newArrayList();

}
