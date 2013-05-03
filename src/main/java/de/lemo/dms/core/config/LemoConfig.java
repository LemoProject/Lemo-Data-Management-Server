/**
 * File ./main/java/de/lemo/dms/core/config/LemoConfig.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mandatory root element of XML configuration file. The DMS project only
 * includes its own {@link DataManagementServer} configuration, as opposed to
 * the App server's ApplicationServer configuration which is omitted here.
 * 
 * @author Leonard Kappe
 */
@XmlRootElement(name = "config")
class LemoConfig {

	/**
	 * DMS configuration
	 */
	@XmlElement(name = "dms")
	public DataManagementServer dataManagementServer;

}
