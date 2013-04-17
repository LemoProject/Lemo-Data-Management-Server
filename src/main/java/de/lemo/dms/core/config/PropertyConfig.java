/**
 * File ./main/java/de/lemo/dms/core/config/PropertyConfig.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Property as key/value pair. Style conforms to properties as found in
 * hibernate.cfg.xml files.
 * 
 * @author Leonard Kappe
 */
@XmlType
class PropertyConfig {

	/**
	 * Property name
	 */
	@XmlAttribute(name = "name", required = true)
	public String key;

	/**
	 * Property value
	 */
	@XmlValue
	public String value;

}
