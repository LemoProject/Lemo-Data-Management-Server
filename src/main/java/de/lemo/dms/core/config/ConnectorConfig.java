package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType
public class ConnectorConfig {

    @XmlAttribute(required = true)
    protected String name;

    @XmlAttribute(name = "platform-id", required = true)
    protected Integer platformId;

    @XmlAttribute(name = "platform-type", required = true)
    protected String platformType;

    @XmlElementWrapper(name = "session-factory", required = true)
    @XmlElement(name = "property")
    protected List<HibernatePropertyConfig> hibernateConfig = Lists.newArrayList();

    @XmlElement(name = "log-path")
    protected String logPath;

    @XmlElement(name = "metadata-path")
    protected String metaDataPath;
}
