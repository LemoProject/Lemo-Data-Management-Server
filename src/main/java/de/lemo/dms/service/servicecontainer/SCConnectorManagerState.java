package de.lemo.dms.service.servicecontainer;

import javax.xml.bind.annotation.XmlRootElement;
import de.lemo.dms.connectors.EConnectorManagerState;

@XmlRootElement
public class SCConnectorManagerState {

	private EConnectorManagerState state;
	private SCConnector updatingConnector;

	public SCConnectorManagerState(EConnectorManagerState state, SCConnector updatingConnector) {
		this.state = state;
		this.updatingConnector = updatingConnector;
	}

	/**
	 * @return the connectorState
	 */
	public EConnectorManagerState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(EConnectorManagerState state) {
		this.state = state;
	}

	/**
	 * @return the updatingConnector
	 */
	public SCConnector getUpdatingConnector() {
		return updatingConnector;
	}

	/**
	 * @param updatingConnector
	 *            the updatingConnector to set
	 */
	public void setUpdatingConnector(SCConnector updatingConnector) {
		this.updatingConnector = updatingConnector;
	}

}
