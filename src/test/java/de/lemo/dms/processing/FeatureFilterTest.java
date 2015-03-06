package de.lemo.dms.processing;

import org.junit.Before;

import de.lemo.dms.core.config.ServerConfiguration;


public class FeatureFilterTest {
	private FeatureFilter featureFilter;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		featureFilter = new FeatureFilter();
	}
}
