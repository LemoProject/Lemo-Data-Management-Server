package de.lemo.dms.processing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.UserInstance;

public class FeatureProcessorTest {
	FeatureProcessor featureProcesor;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		featureProcesor = new FeatureProcessor(0L);
	}
	
	@Test
	public void insertUserAssessmentLogs(){
		List<UserInstance> userInstances = featureProcesor.generateFeaturesForCourseUsers();
		assertNotNull(userInstances);
		int max = 0;
		for(UserInstance userInstance : userInstances){
			if(userInstance.getSegmentProgress()>max){
				max = userInstance.getSegmentProgress();
			}
		}
		assertTrue(max>0);
	}
}
