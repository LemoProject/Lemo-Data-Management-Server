package de.lemo.dms.processing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.UserInstance;


public class FeatureFilterTest {
	private FeatureFilter featureFilter;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		featureFilter = new FeatureFilter();
	}
	
	private UserInstance generateRandomUserInstance(){
		UserInstance userInstance = new UserInstance();
		userInstance.setAnswerCount((int) (Math.random()*10));
		userInstance.setClassId(Math.random()>0.5?1:0);
		userInstance.setCommentCount((int) (Math.random()*10));
		userInstance.setDownVotes((int) (Math.random()*10));
		userInstance.setUpVotes((int) (Math.random()*20));
		userInstance.setWordCount((int) (Math.random()*10));
		userInstance.setLinkCount((int) (Math.random()*10));
		userInstance.setProgressPercentage((int) (Math.random()*100));
		userInstance.setForumUsed(true);
		return userInstance;
	}
	
	@Test
	public void removeProgressWithoutSegments1(){
		ArrayList<UserInstance> userInstances = new ArrayList<UserInstance>();
		UserInstance userInstance = generateRandomUserInstance();
		userInstance.setProgressPercentage(2);
		userInstances.add(userInstance);
		List<UserInstance> userInstancesFiltered = featureFilter.removeProgressWithoutSegments(userInstances);
		assertTrue(userInstancesFiltered.size()==0);
	}
	
	@Test
	public void removeProgressWithoutSegments2(){
		ArrayList<UserInstance> userInstances = new ArrayList<UserInstance>();
		UserInstance userInstance = generateRandomUserInstance();
		userInstance.setProgressPercentage(2);
		userInstances.add(userInstance);
		userInstance = generateRandomUserInstance();
		userInstance.setProgressPercentage(2);
		userInstance.setSegmentProgress(1);
		userInstances.add(userInstance);
		List<UserInstance> userInstancesFiltered = featureFilter.removeProgressWithoutSegments(userInstances);
		assertTrue(userInstancesFiltered.size()==1);
	}
	
	@Test
	public void removeProgressWithoutSegments3(){
		ArrayList<UserInstance> userInstances = new ArrayList<UserInstance>();
		UserInstance userInstance = generateRandomUserInstance();
		userInstance.setProgressPercentage(0);
		userInstances.add(userInstance);
		userInstance = generateRandomUserInstance();
		userInstance.setProgressPercentage(0);
		userInstance.setSegmentProgress(1);
		userInstances.add(userInstance);
		List<UserInstance> userInstancesFiltered = featureFilter.removeProgressWithoutSegments(userInstances);
		assertTrue(userInstancesFiltered.size()==2);
	}
}
