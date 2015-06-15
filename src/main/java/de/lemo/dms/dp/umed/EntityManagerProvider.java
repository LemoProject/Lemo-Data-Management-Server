package de.lemo.dms.dp.umed;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Instantiate
public class EntityManagerProvider {

	private static final Logger logger = LoggerFactory.getLogger(EntityManagerProvider.class);

	@Context
	private BundleContext context;

	@Requires
	private PersistenceProvider persistenceProvider;

	private ServiceRegistration<EntityManagerFactory> emfServiceRegistration;

	private EntityManagerFactory entityManagerFactory;

	@Validate
	private void start() {
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("umed", null);
		emfServiceRegistration = context.registerService(EntityManagerFactory.class, entityManagerFactory, null);
	}

	@Invalidate
	private void stop() {
		if (emfServiceRegistration != null) {
			emfServiceRegistration.unregister();
			emfServiceRegistration = null;
		}
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
			entityManagerFactory = null;
		}
	}

}
