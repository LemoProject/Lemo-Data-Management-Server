/**
 * File ./main/java/de/lemo/dms/core/config/DataManagementServer.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.core.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import com.google.common.collect.Lists;

/**
 * DMS configuration, defines a server name, connectors and provides mining
 * database settings.
 * 
 * @author Leonard Kappe
 */
@XmlType
class DataManagementServer {

	private static final String DEFAULT_NAME = "Lemo Data Management Server";

	/**
	 * Human readable name of the DMS server, may used for identification when
	 * running multiple servers.
	 */
	@XmlAttribute
	public String name = DataManagementServer.DEFAULT_NAME;

	/**
	 * Database settings, uses hibernate-style <code>&lt;property&gt;</code> elements (key/value pairs).
	 */
	@XmlElementWrapper(name = "database", required = true)
	@XmlElement(name = "property")
	public List<PropertyConfig> databaseProperties = Lists.newArrayList();

	@XmlElementWrapper(name = "connectors", required = true)
	@XmlElement(name = "connector")
	public List<Connector> connectors = Lists.newArrayList();

}
