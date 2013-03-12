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
			logger.info(requestInfo);

			StringBuilder parameters = new StringBuilder("Params: ");
			Set<Entry<String, List<String>>> entrySet = request.getFormParameters().entrySet();
			for (Entry<String, List<String>> parameter : entrySet) {
				parameters.append(parameter.getKey()).append(":").append(parameter.getValue()).append(", ");
			}
			if (!entrySet.isEmpty()) {
				parameters.setLength(parameters.length() - ", ".length());
				logger.info(parameters.toString());
			}

		}
		return request;
	}
}
