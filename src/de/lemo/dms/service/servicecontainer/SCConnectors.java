package de.lemo.dms.service.servicecontainer;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container for the REST/JSON Communication to transfer the list of connectors
 * @author Boris Wenzlaff
 *
 */
@XmlRootElement
public class SCConnectors {
	private List<String> connectors;

	public List<String> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<String> connectors) {
		this.connectors = connectors;
	}
	
}