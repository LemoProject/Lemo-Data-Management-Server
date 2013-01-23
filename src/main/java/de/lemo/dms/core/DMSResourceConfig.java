package de.lemo.dms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.ws.rs.Path;
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
 * servlet container) different base URLs are added to the path values in
 * {@link Path} annotations.
 * 
 * @author Leonard Kappe
 */
public class DMSResourceConfig extends DefaultResourceConfig {

	private static final String BASE_URL = "lemo/dms/";

	private static final String SERVICE_BASE_URL = BASE_URL + "services/";
	private static final String SERVICE_PACKAGE = "de.lemo.dms.service";
	private static final String QUESTION_BASE_URL = BASE_URL + "questions/";
	private static final String QUESTION_PACKAGE = "de.lemo.dms.processing.questions";

	private Map<String, Object> resourceSingletons;

	@Override
	public Map<String, Object> getExplicitRootResources() {
		return getResourceSingletons();
	}

	/**
	 * Gets an unmodifiable map of question singletons.
	 * 
	 * @return a map of question singleton services, mapped by URL paths.
	 */
	public Map<String, Object> getResourceSingletons() {
		if (resourceSingletons == null) {
			try {
				resourceSingletons = createResourceSingletons();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return resourceSingletons;
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
		Builder<String, Object> singletons = ImmutableMap.builder();
		Iterable<Entry<String, Class<?>>> singletonResourceMappings =
				Iterables.concat(
						getResourceClasses(SERVICE_PACKAGE, SERVICE_BASE_URL)
								.entrySet(),
						getResourceClasses(QUESTION_PACKAGE, QUESTION_BASE_URL)
								.entrySet());
		for (Entry<String, Class<?>> entry : singletonResourceMappings) {
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
	private HashMap<String, Class<?>> getResourceClasses(String packagePath,
			String baseUrl) {
		HashMap<String, Class<?>> resourceClasses = Maps.newHashMap();
		Set<Class<?>> packageClasses =
				new PackagesResourceConfig(packagePath).getClasses();
		for (Class<?> resource : packageClasses) {
			Path annotation = resource.getAnnotation(Path.class);
			resourceClasses.put(
					baseUrl + CharMatcher.is('/').trimFrom(annotation.value()),
					resource);
		}
		return resourceClasses;
	}

}
