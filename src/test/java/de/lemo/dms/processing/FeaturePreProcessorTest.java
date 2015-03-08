package de.lemo.dms.processing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.UserAssessment;

public class FeaturePreProcessorTest {
	
	private FeaturePreProcessor featurePreProcessor;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		featurePreProcessor = new FeaturePreProcessor();
	}
	
	/* @Test(expected=Exception.class) Test changes database. */
	public void processLogFeatures(){
		featurePreProcessor.processLogFeatures();		
	}
	
	@Test
	public void createAssessmentsFromLearningAttributes(){
		List<UserAssessment> userAssessments = new ArrayList<UserAssessment>();		
		userAssessments = featurePreProcessor.createAssessmentsFromLearningAttributes();
		assertTrue(userAssessments.size()>0);
	}	
}