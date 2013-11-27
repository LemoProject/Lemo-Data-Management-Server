/**
 * File ./src/main/java/de/lemo/dms/connectors/IConnector.java
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
 * File ./main/java/de/lemo/dms/connectors/IConnector.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.List;

/**
 * Interface for the connector classes
 */
public interface IConnector {

	/**
	 * Tests the configured connection.
	 * 
	 * @param conf
	 *            the database configuration object, holding connection settings
	 * @return true, if successful
	 */
	boolean testConnections();

	/**
	 * Gets the data.
	 */
	void getData();

	/**
	 * Update data.
	 * 
	 * @param conf
	 *            the database configuration object, holding connection settings
	 * @param fromTimestamp
	 *            the from timestamp
	 */
	@Deprecated
	void updateData(long fromTimestamp);

	Long getPlatformId();

	Long getPrefix();

	String getName();

	ESourcePlatform getPlattformType();

	boolean isFilterEnabled();

	List<Long> getCourseIdFilter();
	
	List<String> getCourseLoginFilter();
	
	void setCourseIdFilter(List<Long> courses);

}
