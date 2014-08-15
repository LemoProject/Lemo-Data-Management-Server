/**
 * File ./src/main/java/de/lemo/dms/core/config/LemoConfig.java
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
