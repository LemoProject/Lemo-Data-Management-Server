/**
 * File ./src/main/java/de/lemo/dms/connectors/EConnectorManagerState.java
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
 * File ./main/java/de/lemo/dms/connectors/EConnectorState.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

/**
 * States of the connector update process.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public enum EConnectorManagerState {
	/**
	 * No connector update is running, the user may start one.
	 */
	READY,
	/**
	 * A connector update is currently running, the user has to wait for it to finish.
	 */
	IN_PROGRESS,
	/**
	 * No connector update is running, there are no connector to update.
	 */
	NO_CONNECTORS,
	/**
	 * The configuration is faulty or missing.
	 */
	CONFIGURATION_ERROR;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	};
}
