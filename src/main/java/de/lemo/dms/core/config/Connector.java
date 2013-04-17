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
	@XmlElement
	@XmlList
	public List<Long> courseIdFilter = Lists.newArrayList();

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
