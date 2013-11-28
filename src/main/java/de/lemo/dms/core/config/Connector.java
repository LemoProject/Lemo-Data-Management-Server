/**
 * File ./src/main/java/de/lemo/dms/core/config/Connector.java
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
 * File ./main/java/de/lemo/dms/core/config/Connector.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;
import com.google.common.collect.Lists;

/**
 * Configuration for {@link de.lemo.dms.connectors.IConnector} implementations.
 * 
 * @author Leonard Kappe
 */
@XmlType
class Connector {

	/**
	 * Human readable name of the connector.
	 */
	@XmlAttribute(required = true)
	public String name;

	/**
	 * Unique ID of the connector.
	 */
	@XmlAttribute(name = "platform-id", required = true)
	public Long platformId;

	/**
	 * Type of the LMS platform, must match a value of {@link de.lemo.dms.connectors.ESourcePlatform}.
	 */
	@XmlAttribute(name = "platform-type", required = true)
	public String platformType;

	/**
	 * List of connector IDs to be loaded by the connector. If no IDs are provided, all courses will be loaded.
	 */
	@XmlElement(name="course-id-filter")
	@XmlList
	public List<Long> courseIdFilter = Lists.newArrayList();
	
	@XmlElement(name="course-login-filter")
	@XmlList
	public List<String> courseLoginFilter = Lists.newArrayList();

	/**
	 * Hibernate-style <code>&lt;property&gt;</code> elements, used for source
	 * database or file based server logs.
	 */
	@XmlElement(name = "property", required = true)
	public List<PropertyConfig> properties = Lists.newArrayList();

	@Override
	public String toString() {
		return this.platformId + "-" + this.platformType + "-" + this.name;
	}
}
