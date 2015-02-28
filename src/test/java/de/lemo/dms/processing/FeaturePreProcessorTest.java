package de.lemo.dms.processing;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;

public class FeaturePreProcessorTest {
	
	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
	}
	
	@Test(expected=Exception.class)
	public void processLogFeatures(){
		new FeaturePreProcessor().processLogFeatures();		
	}
	
}