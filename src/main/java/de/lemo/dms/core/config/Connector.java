package de.lemo.dms.core.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType
class Connector {

    @XmlAttribute(required = true)
    public String name;

    @XmlAttribute(name = "platform-id", required = true)
    public Long platformId;

    @XmlAttribute(name = "platform-type", required = true)
    public String platformType;

    @XmlElement(name = "property", required = true)
    public List<PropertyConfig> properties = Lists.newArrayList();

    @Override
    public String toString() {
        return platformId + "-" + platformType + "-" + name;
    }
}
