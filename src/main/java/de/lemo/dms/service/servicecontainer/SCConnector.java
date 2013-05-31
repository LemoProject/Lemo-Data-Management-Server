package de.lemo.dms.service.servicecontainer;

import javax.xml.bind.annotation.XmlRootElement;
import de.lemo.dms.connectors.IConnector;

@XmlRootElement
public class SCConnector {

	private String name;
	private Long platformId;
	private String platformName;

	public SCConnector(IConnector connector) {
		name = connector.getName();
		platformId = connector.getPlatformId();
		platformName = connector.getPlattformType().toString();
	}

	@Override
	public String toString() {
		return platformId + " / " + platformName + " - " + name;
	}

	public String getName() {
		return name;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

}
