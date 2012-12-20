package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Config {

    @XmlElement(name = "apps")
    ApplicationServer applicationServer;

    @XmlElement(name = "dms")
    DataManagementServer dataManagementServer;
}
