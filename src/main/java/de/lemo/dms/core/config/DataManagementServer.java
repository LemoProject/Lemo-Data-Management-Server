/**
 * File ./src/main/java/de/lemo/dms/core/config/DataManagementServer.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/core/config/DataManagementServer.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
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

	// 5 minutes
	private static final int DEFAULT_PATH_ANALYSIS_TIMEOUT = 60000 * 5;

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

	/**
	 * Definitions of platform connectors.
	 */
	@XmlElementWrapper(name = "connectors", required = true)
	@XmlElement(name = "connector")
	public List<Connector> connectors = Lists.newArrayList();

	/**
	 * Timeout for path analysis threads in seconds. If omitted, the default timeout value is 5 minutes.
	 * A value less than one indicates that no timeout should be used.
	 */
	@XmlElement(name = "path-analysis-timeout")
	public int pathAnalysisTimeout = DEFAULT_PATH_ANALYSIS_TIMEOUT;

}
