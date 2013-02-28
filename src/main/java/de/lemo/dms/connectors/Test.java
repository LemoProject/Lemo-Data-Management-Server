/**
 * File ./main/java/de/lemo/dms/connectors/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsViger;
import de.lemo.dms.processing.questions.QPerformanceBoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

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
	
	public ResultListLongObject authentificateUser(String login) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		final String loginHash = Encoder.createMD5(login);

		final Criteria criteria = session.createCriteria(UserMining.class, "users");
		criteria.add(Restrictions.eq("users.login", loginHash));
		
		ArrayList<UserMining> results = (ArrayList<UserMining>) criteria.list();

		ResultListLongObject res;
		
		if(results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			l.add(results.get(0).getId());
			res = new ResultListLongObject(l);
		}
		else {
			res = new ResultListLongObject();
		}
		return res;
	}
	
	public ResultListLongObject getTeachersCourses(Long id) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		ResultListLongObject result;
		
		final Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.eq("cu.user.id", id));
		
		ArrayList<CourseUserMining> results = (ArrayList<CourseUserMining>) criteria.list();

		if(results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			for(CourseUserMining cu : results) {
				l.add(cu.getCourse().getId());
			}
			result = new ResultListLongObject(l);
		}
		else {
			result = new ResultListLongObject();	
		}
		return result;
	}
	
	/**
	 * Tests the Chemgapedia-connector. Configurations have to be altered accordingly.
	 */
	public void runChemConn()
	{

		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_CHEM);
		connector.getData();
	}

	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE19);
		connector.updateData(1338000000L);
	}

	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodleNumericConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE_NUMERIC);
		connector.getData();
	}

	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 */
	public void runMoodle23Conn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_MOODLE23);
		connector.getData();
	}

	/**
	 * Tests the Clix(2010)-connector. Configurations have to be altered accordingly.
	 */
	public void runClixConn()
	{
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(Test.ID_CLIX);
		connector.getData();
	}

	public void test()
	{
		
	}

	public void calculateMeichsner()
	{
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final ArrayList<Long> cids = new ArrayList<Long>();
		cids.add(100117945446L);
		cids.add(100113617310L);
		cids.add(100110921956L);
		cids.add(10018074949L);
		cids.add(10014667155L);

		final Criteria crit = session.createCriteria(ResourceLogMining.class, "logs");
		crit.add(Restrictions.in("logs.course.id", cids));
		logger.info("Reading DB");
		@SuppressWarnings("unchecked")
		final List<ResourceLogMining> l = crit.list();
		logger.info("Found " + l.size() + " courses.");

		final HashSet<String> hSet = new HashSet<String>();
		for (final ResourceLogMining resL : l)
		{
			if ((resL.getResource() != null)
					&& resL.getResource().getTitle().contains("Grundlagen des Projektmanagements"))
			{
				final String id = resL.getCourse().getTitle() + "-" + resL.getResource().getTitle();
				hSet.add(id);
			}
		}

		for (final String s : hSet)
		{
			logger.info(s);
		}

	}

	public void testViger()
	{
		final QFrequentPathsViger qfpv = new QFrequentPathsViger();
		final ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		final ArrayList<Long> users = new ArrayList<Long>();
		final ArrayList<String> types = new ArrayList<String>();
		final Long minLength = 0L;
		final Long maxLength = 1000L;
		final double minSup = 1;

		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);

	}

	public void testBide()
	{
		final QFrequentPathsBIDE qfpv = new QFrequentPathsBIDE();
		final ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		final ArrayList<Long> users = new ArrayList<Long>();
		final ArrayList<String> types = new ArrayList<String>();
		final Long minLength = 0L;
		final Long maxLength = 1000L;
		final double minSup = 0.5;

		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);

	}

	public void testHisto()
	{
		final QPerformanceBoxPlot ph = new QPerformanceBoxPlot();
		final ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(11476L);
		final ArrayList<Long> quizzes = new ArrayList<Long>();
		quizzes.add(11114861L);
		quizzes.add(11114282L);
		quizzes.add(1411888L);
		quizzes.add(1411939L);

		final ResultListBoxPlot res = ph.compute(courses, new ArrayList<Long>(), quizzes, 100L, 0L, 1500000000L);
		logger.info(res.getElements().size());

	}

	public void testService()
	{
		final ArrayList<String> res = new ArrayList<String>();
		final ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(11476L);
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final Criteria criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));

		@SuppressWarnings("unchecked")
		final ArrayList<ICourseRatedObjectAssociation> list = (ArrayList<ICourseRatedObjectAssociation>) criteria
				.list();

		for (final ICourseRatedObjectAssociation obj : list)
		{
			res.add(obj.getRatedObject().getClass().getSimpleName());
			res.add(obj.getRatedObject().getPrefix().toString());
			res.add(obj.getRatedObject().getId() + "");
			res.add(obj.getRatedObject().getTitle());
		}
		logger.info("Starting test");
	}

	public void run()
	{
		logger.info("Starting test");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		this.runChemConn();
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
