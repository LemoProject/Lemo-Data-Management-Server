package de.lemo.dms.processing.questions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;

public class QDatabaseTest {

	private QDatabase instance;
	private Long testCourseId;
	private Long startTime;
	private Long endTime;
	private Long trainCourseId;

	@Before
	public void setUp() {
		ServerConfiguration.getInstance().loadConfig("/lemo");
		instance = new QDatabase();
	}

	@Test
	public void courseSize() {

		testCourseId = 0L;
		trainCourseId =  0L;
		startTime = 0L;
		endTime = Long.MAX_VALUE;
		ResultListUserInstance result = instance.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(result);
	}

}
