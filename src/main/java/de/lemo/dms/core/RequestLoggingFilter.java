/**
 * File ./src/main/java/de/lemo/dms/core/RequestLoggingFilter.java
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

package de.lemo.dms.core;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class RequestLoggingFilter implements ContainerRequestFilter {

	protected final Logger logger = Logger.getLogger("Request");

	@Override
	public ContainerRequest filter(final ContainerRequest request) {
		if (logger.isInfoEnabled()) {
			String requestInfo = request.getPath() + " (" + request.getMethod() +
					(request.getMediaType() == null ? "" : " " + request.getMediaType()) + ")";
			logger.debug(requestInfo);

			StringBuilder parameters = new StringBuilder("Params: ");
			Set<Entry<String, List<String>>> entrySet = request.getFormParameters().entrySet();
			for (Entry<String, List<String>> parameter : entrySet) {
				parameters.append(parameter.getKey()).append(":").append(parameter.getValue()).append(", ");
			}
			if (!entrySet.isEmpty()) {
				parameters.setLength(parameters.length() - ", ".length());
				logger.debug(parameters.toString());
			}

		}
		return request;
	}
}
