package de.lemo.dms.processing.questions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.FeatureProcessor;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;

public class QDatabaseTest {

	private QDatabase qDatabase;
	private Long testCourseId;
	private Long startTime;
	private Long endTime;
	private Long trainCourseId;

	@Before
	public void setUp() {
		ServerConfiguration.getInstance().loadConfig("/lemo");
		testCourseId = 2L;
		trainCourseId =  2L;
		startTime = 0L;
		endTime = Long.MAX_VALUE;
		qDatabase = new QDatabase();
	}
	
	//@Test
	public void generateUserInstancesFromFeatures(){
		List<UserInstance> studentInstances = qDatabase.generateUserInstancesFromFeatures(testCourseId);
		assertNotNull(studentInstances);
		assertTrue(ArrayList.class.isInstance(studentInstances));
	}
	
	@Test
	public void computeOneCourse(){
		ResultListUserInstance resultLarge  = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(resultLarge.getElements());		
	}

	//@Test
	public void compute() {
		ResultListUserInstance resultLarge  = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(resultLarge.getElements());
		startTime=1376263683L;
		endTime=1379643589L;
		ResultListUserInstance result = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		System.out.println("Large: "+resultLarge.getElements().size());
		System.out.println("Smaller: "+result.getElements().size());
		assertTrue(resultLarge.getElements().size()>result.getElements().size());		
	}
}
