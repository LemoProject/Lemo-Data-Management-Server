package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType
public class ApplicationServer {

    private static final String DEFAULT_NAME = "Lemo Application Server";

    @XmlAttribute
    protected String name = DEFAULT_NAME;

    @XmlElement(name = "dms-url")
    protected String dataManagementServerURL;

    @XmlElementWrapper(name = "session-factory", required = true)
    @XmlElement(name = "property")
    protected List<HibernateProperty> hibernateConfig = Lists.newArrayList();
}
