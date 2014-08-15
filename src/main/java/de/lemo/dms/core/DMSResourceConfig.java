/**
 * File ./src/main/java/de/lemo/dms/core/DMSResourceConfig.java
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
 * File ./main/java/de/lemo/dms/core/DMSResourceConfig.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;

/**
 * Resource configuration used to discover and load web services when not
 * running in a servlet container environment. Creates singletons of question
 * and service classes.
 * To match servlets defined in the web.xml file (which isn't used without a
 * servlet container) different base URLs are added to the path values in {@link Path} annotations.
 * 
 * @author Leonard Kappe
 */
public class DMSResourceConfig extends DefaultResourceConfig {

	private static final String BASE_URL = "lemo/dms/";
	private static final String SERVICE_BASE_URL = DMSResourceConfig.BASE_URL + "services/";
	private static final String SERVICE_PACKAGE = "de.lemo.dms.service";
	private static final String QUESTION_BASE_URL = DMSResourceConfig.BASE_URL + "questions/";
	private static final String QUESTION_PACKAGE = "de.lemo.dms.processing.questions";
	private final Logger logger = Logger.getLogger(this.getClass());

	private Map<String, Object> resourceSingletons;

	@Override
	public Map<String, Object> getExplicitRootResources() {
		return this.getResourceSingletons();
	}

	/**
	 * Gets an unmodifiable map of question singletons.
	 * 
	 * @return a map of question singleton services, mapped by URL paths.
	 */
	public Map<String, Object> getResourceSingletons() {
		if (this.resourceSingletons == null) {
			try {
				this.resourceSingletons = this.createResourceSingletons();
			} catch (final InstantiationException e) {
				logger.error(e.getMessage());
				logger.error(e.getStackTrace());
			} catch (final IllegalAccessException e) {
				logger.error(e.getMessage());
				logger.error(e.getStackTrace());
			}
		}
		return this.resourceSingletons;
	}

	/**
	 * Creates singletons of services and questions.
	 * 
	 * @return Mapping of URLs to their singleton instances
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Map<String, Object> createResourceSingletons()
			throws InstantiationException, IllegalAccessException {
		final Builder<String, Object> singletons = ImmutableMap.builder();
		final Iterable<Entry<String, Class<?>>> singletonResourceMappings =
				Iterables
						.concat(
								this.getResourceClasses(DMSResourceConfig.SERVICE_PACKAGE,
										DMSResourceConfig.SERVICE_BASE_URL)
										.entrySet(),
								this.getResourceClasses(DMSResourceConfig.QUESTION_PACKAGE,
										DMSResourceConfig.QUESTION_BASE_URL)
										.entrySet());
		for (final Entry<String, Class<?>> entry : singletonResourceMappings) {
			singletons.put(entry.getKey(), entry.getValue().newInstance());
		}
		return singletons.build();
	}

	/**
	 * Scan a package for {@link Path} annotations and map the types to their
	 * paths, prefixed by the given base URL.
	 * 
	 * @param packagePath
	 *            the package to scan
	 * @param baseUrl
	 *            URL prefix to be added to the annotations' values
	 * @return every {@link Path}-annotated class in the package, mapped to the
	 *         annotations value prefixed by the base URL
	 */
	private Map<String, Class<?>> getResourceClasses(final String packagePath,
			final String baseUrl) {
		final HashMap<String, Class<?>> resourceClasses = Maps.newHashMap();
		final Set<Class<?>> packageClasses =
				new PackagesResourceConfig(packagePath).getClasses();
		for (final Class<?> resource : packageClasses) {
			final Path annotation = resource.getAnnotation(Path.class);
			resourceClasses.put(
					baseUrl + CharMatcher.is('/').trimFrom(annotation.value()),
					resource);
		}
		return resourceClasses;
	}

}
