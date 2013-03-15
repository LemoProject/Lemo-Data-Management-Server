/**
 * File ./main/java/de/lemo/dms/connectors/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.questions.QLearningObjectUsage;
import de.lemo.dms.processing.questions.QPerformanceUserTestBoxPlot;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;
import de.lemo.dms.service.ServiceCourseDetails;
import de.lemo.dms.service.ServiceCourseTitleSearch;
import de.lemo.dms.service.ServiceUserInformation;


/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 */
public class Test {

	private static final Long ID_MOODLE23 = 4L;
	private static final Long ID_CHEM = 3L;
	private static final Long ID_MOODLE_NUMERIC = 5L;
	private static final Long ID_MOODLE19 = 2L;
	private static final Long ID_CLIX = 6L;
	final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodle23Conn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE23);
		connector.getData();
		
	}
	
	/**
	 * Tests the Clix2010-connector. Configurations have to be altered accordingly.
	 */
	public void runClixConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_CLIX);
		List<Long> courses = new ArrayList<Long>();
		
		courses.add(18508964L);
		courses.add(21040950L);
		courses.add(21040967L);
		
		/*courses.add(4667155L);
		courses.add(8074949L);
		courses.add(10921956L);
		courses.add(13617310L);
		courses.add(17945446L);
		*/
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}

	
	@SuppressWarnings("unchecked")
	public void test()
	{
		QLearningObjectUsage qlou = new QLearningObjectUsage();
		List<Long> courses = new ArrayList<Long>();
		
		courses.add(11476L);
		
		
		qlou.compute(courses, new ArrayList<Long>(), new ArrayList<String>(), 0L, 1500000000L);
		
	
		ServiceCourseTitleSearch scts = new ServiceCourseTitleSearch();
		scts.getCoursesByText("Inform", null, null);
		
	}
	

	public void run()
	{
		logger.info("Starting test");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		this.test();
		logger.info("Test finished");
	}

	/**
	 * main method for test case 
	 * TODO move to unit test
	 * @param args
	 */
	public static void main(final String[] args)
	{

		final Test t = new Test();
		t.run();
	}

}
