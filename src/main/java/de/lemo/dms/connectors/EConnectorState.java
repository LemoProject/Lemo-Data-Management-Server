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
public enum EConnectorState {
	/**
	 * No connector update is running, the user may start one.
	 */
	ready,
	/**
	 * A connector update is currently running, the user has to wait for it to finish.
	 */
	progress,
	/**
	 * No connector update is running, there are no connector to update.
	 */
	noconnector,
	/**
	 * The configuration is faulty or missing.
	 */
	noconfiguration
}
