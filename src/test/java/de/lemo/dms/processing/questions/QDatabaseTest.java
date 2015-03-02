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
		testCourseId = 0L;
		trainCourseId =  0L;
		startTime = 0L;
		endTime = Long.MAX_VALUE;
		qDatabase = new QDatabase();
	}
	
	@Test
	public void generateUserInstancesFromFeatures(){
		List<UserInstance> studentInstances = qDatabase.generateUserInstancesFromFeatures(testCourseId);
		assertNotNull(studentInstances);
		assertTrue(ArrayList.class.isInstance(studentInstances));
	}

	@Test
	public void courseSize() {

		ResultListUserInstance result = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(result);
	}
	
	

}