/**
 * File ./main/java/de/lemo/dms/connectors/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.processing.questions.QActivityResourceType;
import de.lemo.dms.processing.questions.QCourseActivity;
import de.lemo.dms.processing.questions.QCumulativeUserAccess;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QLearningObjectUsage;
import de.lemo.dms.processing.questions.QPerformanceHistogram;
import de.lemo.dms.processing.questions.QPerformanceUserTest;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.service.ServiceCourseDetails;
import de.lemo.dms.service.ServiceCourseTitleSearch;


/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 */
public class Test {

	private static final Long ID_MOODLE23 = 4L;
	private static final Long ID_MOODLE_NUMERIC = 5L;
	private static final Long ID_MOODLE19 = 2L;
	private static final Long ID_CLIX = 6L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodle23Conn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE23);
		Set<Long> courses = new HashSet<Long>();
		/*courses.add(441L);
		courses.add(165L);
		courses.add(169L);
		courses.add(167L);
		courses.add(176L);
		courses.add(177L);
		courses.add(178L);
		courses.add(179L);
		courses.add(180L);
		courses.add(189L);
		courses.add(20L);
		courses.add(19L);
		courses.add(316L);
		courses.add(318L);
		courses.add(366L);
		courses.add(429L);
		courses.add(227L);
		courses.add(514L);
		courses.add(515L);
		courses.add(516L);
		courses.add(517L);
		courses.add(518L);
		courses.add(519L);
		courses.add(520L);
		courses.add(521L);
		courses.add(522L);
		courses.add(523L);
		courses.add(524L);
		courses.add(525L);
		courses.add(526L);
		courses.add(527L);
		courses.add(528L);
		courses.add(529L);
		courses.add(530L);
		courses.add(532L);
		courses.add(533L);
		courses.add(534L);
		courses.add(535L);
		courses.add(536L);
		courses.add(537L);
		courses.add(538L);
		courses.add(539L);
		courses.add(550L);
		courses.add(399L);
		courses.add(776L);
		courses.add(231L);
		courses.add(346L);
		courses.add(373L);
		courses.add(493L);
		courses.add(494L);
		courses.add(168L);
		courses.add(212L);
		courses.add(366L);
		courses.add(232L);
		courses.add(142L);
		courses.add(441L);
		courses.add(461L);
		courses.add(481L);
		courses.add(513L);
		courses.add(217L);
		courses.add(308L);
		courses.add(327L);
		courses.add(328L);
		courses.add(382L);
		courses.add(612L);
		courses.add(213L);
		courses.add(218L);
		courses.add(495L);
		courses.add(496L);
		
		logger.info(courses.add(43L));
		logger.info(courses.add(73L));
		logger.info(courses.add(142L));
		logger.info(courses.add(168L));
		logger.info(courses.add(212L));
		logger.info(courses.add(238L));
		logger.info(courses.add(239L));
		logger.info(courses.add(240L));
		logger.info(courses.add(273L));
		logger.info(courses.add(366L));
		logger.info(courses.add(434L));
		logger.info(courses.add(441L));
		logger.info(courses.add(481L));
		logger.info(courses.add(513L));
		logger.info(courses.add(533L));
		logger.info(courses.add(550L));
		logger.info(courses.add(572L));
		logger.info(courses.add(574L));
		logger.info(courses.add(732L));
		*/
		courses.add(73L);
		courses.add(168L);
		courses.add(238L);
		courses.add(239L);
		courses.add(240L);
		courses.add(273L);
		courses.add(366L);
		courses.add(142L);
		courses.add(434L);
		courses.add(441L);
		courses.add(212L);
		courses.add(43L);
		courses.add(481L);
		courses.add(513L);
		courses.add(534L);
		courses.add(533L);
		courses.add(550L);
		courses.add(572L);
		courses.add(574L);
		courses.add(732L);
		courses.add(165L);
		courses.add(169L);
		courses.add(167L);
		courses.add(176L);
		courses.add(177L);
		courses.add(178L);
		courses.add(179L);
		courses.add(180L);
		courses.add(189L);
		courses.add(20L);
		courses.add(19L);
		courses.add(316L);
		courses.add(318L);
		courses.add(366L);
		courses.add(429L);
		courses.add(227L);
		courses.add(514L);
		courses.add(515L);
		courses.add(516L);
		courses.add(517L);
		courses.add(518L);
		courses.add(519L);
		courses.add(520L);
		courses.add(521L);
		courses.add(522L);
		courses.add(523L);
		courses.add(524L);
		courses.add(525L);
		courses.add(526L);
		courses.add(527L);
		courses.add(528L);
		courses.add(529L);
		courses.add(530L);
		courses.add(532L);
		courses.add(533L);
		courses.add(534L);
		courses.add(535L);
		courses.add(536L);
		courses.add(537L);
		courses.add(538L);
		courses.add(539L);
		courses.add(550L);
		courses.add(399L);
		courses.add(776L);
		courses.add(231L);
		courses.add(346L);
		courses.add(373L);
		courses.add(493L);
		courses.add(494L);
		courses.add(168L);
		courses.add(212L);
		courses.add(366L);
		courses.add(232L);
		courses.add(142L);
		courses.add(441L);
		courses.add(461L);
		courses.add(481L);
		courses.add(513L);
		courses.add(217L);
		courses.add(308L);
		courses.add(327L);
		courses.add(328L);
		courses.add(382L);
		courses.add(612L);
		courses.add(213L);
		courses.add(218L);
		courses.add(495L);
		courses.add(496L);
		
		connector.setCourseIdFilter(new ArrayList(courses));
		
		connector.getData();
		
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE19);
		List<Long> courses = new ArrayList<Long>();
		courses.add(476L);
		courses.add(2200L);
		courses.add(3905L);
		
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleNumericConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE_NUMERIC);
		List<Long> courses = new ArrayList<Long>();
		courses.add(164L);
		courses.add(169L);
		courses.add(170L);
		courses.add(171L);
		courses.add(172L);
		courses.add(173L);
		courses.add(174L);
		courses.add(224L);
		courses.add(236L);
		courses.add(270L);
		courses.add(303L);
		courses.add(306L);
		courses.add(310L);
		courses.add(312L);
		courses.add(328L);
		courses.add(335L);
		courses.add(397L);
		courses.add(402L);
		courses.add(408L);
		courses.add(421L);
		courses.add(424L);
		courses.add(434L);
		courses.add(440L);
		courses.add(442L);
		courses.add(491L);
		courses.add(517L);
		courses.add(534L);
		courses.add(536L);
		courses.add(537L);
		courses.add(538L);
		courses.add(547L);
		courses.add(548L);
		courses.add(549L);
		courses.add(553L);
		courses.add(580L);
		courses.add(593L);
		courses.add(615L);
		courses.add(616L);
		courses.add(668L);
		courses.add(713L);
		courses.add(717L);
		courses.add(732L);
		courses.add(755L);
		courses.add(766L);
		courses.add(767L);
		courses.add(768L);
		courses.add(789L);
		courses.add(792L);
		
		connector.setCourseIdFilter(courses);
		
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
		
		
		
		courses.add(4667155L);
		courses.add(8074949L);
		courses.add(10921956L);
		courses.add(13617310L);
		courses.add(17945446L);
		
		connector.setCourseIdFilter(courses);
		
		connector.getData();
		
	}

	
	public void test()
	{
		QPerformanceHistogram qput = new QPerformanceHistogram();
		QFrequentPathsBIDE bid = new QFrequentPathsBIDE();
		QCumulativeUserAccess cua = new QCumulativeUserAccess();
		QCourseActivity qlou = new QCourseActivity();
		QActivityResourceType lou = new QActivityResourceType();
		QActivityResourceType art = new QActivityResourceType();
		QLearningObjectUsage u = new QLearningObjectUsage();
		
		List<Long> gender = new ArrayList<Long>();
		
		List<Long> courses = new ArrayList<Long>();
		List<Long> users = new ArrayList<Long>();
	//	users.add(2L);
		courses.add(11476L);

		
		ArrayList<String> types = new ArrayList<String>();
		types.add("FORUM");
		

		
		ServiceCourseDetails scd = new ServiceCourseDetails();
		
		//CourseObject co = scd.getCourseDetails(11476L);
		
		List<Long> teachers = new ArrayList<Long>();
		teachers.add(132278L);
		teachers.add(132344L);
		teachers.add(132300L);
		teachers.add(132349L);
		teachers.add(132342L);
		teachers.add(13180L);
		
		List<Long> roles = new ArrayList<Long>();
		roles.add(133L);

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		// Create criteria for log-file-search
		Criteria criteria = session.createCriteria(CourseUserMining.class, "course");
		criteria.add(Restrictions.in("course.user.id", teachers));
		criteria.add(Restrictions.in("course.role.id", roles));
		
		int i = 0;
		for(CourseUserMining l : (ArrayList<CourseUserMining>)criteria.list())
		{
			String s = (""+l.getCourse().getId()).substring(2);
			System.out.println("courses.add(" + s + "L);");
			i++;
		}
		
		System.out.println(i);
	
		ServiceCourseTitleSearch scts = new ServiceCourseTitleSearch();
		scts.getCoursesByText("Inform", null, null);
		
	}
	

	public void run()
	{
		this.logger.info("Starting Import");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		this.runMoodle23Conn();
		this.logger.info("Import finished");
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
