/**
 * File ./src/main/java/de/lemo/dms/service/servicecontainer/SCConnector.java
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
