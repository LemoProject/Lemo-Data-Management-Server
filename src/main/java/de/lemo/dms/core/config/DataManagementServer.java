package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType
public class DataManagementServer {

    private static final String DEFAULT_NAME = "Lemo Data Management Server";

    @XmlAttribute
    protected String name = DEFAULT_NAME;

    @XmlElementWrapper(name = "session-factory", required = true)
    @XmlElement(name = "property")
    protected List<HibernateProperty> hibernateConfig = Lists.newArrayList();

    @XmlElementWrapper(name = "connectors", required = true)
    @XmlElement(name = "connector")
    protected List<Connector> connectors = Lists.newArrayList();

}
