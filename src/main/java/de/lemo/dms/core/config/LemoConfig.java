package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class LemoConfig {

    @XmlElement(name = "apps")
    protected ApplicationServer applicationServer;

    @XmlElement(name = "dms")
    protected DataManagementServer dataManagementServer;
}
