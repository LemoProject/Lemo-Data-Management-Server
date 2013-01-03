package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class LemoConfig {

    @XmlElement(name = "apps")
    protected ApplicationServerConfig applicationServer;

    @XmlElement(name = "dms")
    protected DataManagementServerConfig dataManagementServer;
}
