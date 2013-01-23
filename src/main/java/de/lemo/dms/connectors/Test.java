package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsViger;
import de.lemo.dms.processing.questions.QPerformanceBoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;

/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 *
 */
public class Test {
	
	private static final Long ID_MOODLE23 = 4L;
	private static final Long ID_CHEM = 3L;
	private static final Long ID_MOODLE_NUMERIC = 5L;
	private static final Long ID_MOODLE19 = 2L;
	private static final Long ID_CLIX = 6L;

	/**
	 * Tests the Chemgapedia-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runChemConn()
	{

		//ServerConfiguration.getInstance().loadConfig("/lemo");
		IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_CHEM);
		connector.getData();
		/*
		String logs = "C:\\Users\\s.schwarzrock\\Desktop\\120614\\130104_lemojoin_10K_1.log";
		//int i = 0;
		//for(int i = 1 ; i < 6; i++)
		//{
		DBConfigObject sourceConf = new DBConfigObject();
		sourceConf.setProperty("lemo.log_file_path", logs);
		sourceConf.setProperty("lemo.resource_metadata_path", "C:\\Users\\s.schwarzrock\\Desktop\\vsc");
		sourceConf.setProperty("lemo.filter_log_file", "true");
		sourceConf.setProperty("lemo.process_metadata", "true");
		sourceConf.setProperty("lemo.process_log_file", "true");
		
		// IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_CHEM);
		ConnectorChemgapedia cm = new ConnectorChemgapedia(sourceConf);
		cm.setPlatformId(100L);
		cm.setName("chem-test");
		cm.setPrefix(101L);
		
		cm.updateData(0L);
		*/
	}
	
	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodleConn()
	{
		//ServerConfiguration.getInstance().loadConfig("/lemo");
		IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_MOODLE19);
		connector.updateData(1338000000L);
	}
	
	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodleNumericConn()
	{
		IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_MOODLE_NUMERIC);
		connector.getData();
				//connector.updateData(1338000000);
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runMoodle23Conn()
	{
		IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_MOODLE23);
		connector.getData();
 		// connector.updateData(1338000000);
	}
	
	/**
	 * Tests the Clix(2010)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public void runClixConn()
	{
		IConnector connector = ConnectorManager.getInstance().getConnectorById(ID_CLIX);
		connector.getData();
 		// connector.updateData(1338000000);
	}
	
	public void test()
	{
//		Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
//        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
//        session.clear();
//		
//		Query pers = session.createQuery("from Person x order by x.id asc");
//        List<?> person = pers.list();	        
//        System.out.println("Person tables: " + person.size()); 
	}
	
	public void calculateMeichsner()
	{
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		Session session = dbHandler.getMiningSession();
		
		ArrayList<Long> cids = new ArrayList<Long>();
		cids.add(100117945446L);
		cids.add(100113617310L);
		cids.add(100110921956L);
		cids.add(10018074949L);
		cids.add(10014667155L);
		
		Criteria crit = session.createCriteria(ResourceLogMining.class, "logs");
		crit.add(Restrictions.in("logs.course.id", cids));
		System.out.println("Reading DB");
		@SuppressWarnings("unchecked")
        List<ResourceLogMining> l = crit.list();
		System.out.println("Found "+l.size()+" courses.");
		
		HashSet<String> hSet = new HashSet<String>();
		for(ResourceLogMining resL : l)
		{
			if(resL.getResource() != null && resL.getResource().getTitle().contains("Grundlagen des Projektmanagements"))
			{
				String id = resL.getCourse().getTitle() + "-" + resL.getResource().getTitle();
				hSet.add(id);
			}
		}
		
		for(String s : hSet)
		{
			System.out.println(s);
		}
		
	}
	
		
//	public static void test2()
//	{
//		QCourseActivity qca = new QCourseActivity();
//		List<Long> courses = new ArrayList<Long>();
//		courses.add(112200L);
//		courses.add(11476L);
//		List<Long> roles = new ArrayList<Long>();
//		Long startTime = 0L;
//		Long endTime = 1500000000L;
//		int resolution = 100;
//		List<String> resourceTypes = new ArrayList<String>();
//		qca.compute(courses, roles, startTime, endTime, resolution, resourceTypes);
//	}
	
	public void testViger()
	{
		QFrequentPathsViger qfpv = new QFrequentPathsViger();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		ArrayList<Long> users = new ArrayList<Long>();
		ArrayList<String> types = new ArrayList<String>();
		Long minLength = 0L;
		Long maxLength = 1000L;
		double minSup = 1;
		
		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);
		
	}
	
	public void testBide()
	{
		QFrequentPathsBIDE qfpv = new QFrequentPathsBIDE();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		ArrayList<Long> users = new ArrayList<Long>();
		ArrayList<String> types = new ArrayList<String>();
		Long minLength = 0L;
		Long maxLength = 1000L;
		double minSup = 0.5;
		
		qfpv.compute(courses, users, types, minLength, maxLength, minSup, false, 0L, 1500000000L);
		
	}
	
	public void testHisto()
	{
		QPerformanceBoxPlot ph = new QPerformanceBoxPlot();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(11476L);
		ArrayList<Long> quizzes = new ArrayList<Long>();
		quizzes.add(11114861L);
		quizzes.add(11114282L);
		quizzes.add(1411888L);
		quizzes.add(1411939L);
		
		ResultListBoxPlot res = ph.compute(courses, new ArrayList<Long>(), quizzes, 100, 0L, 1500000000L);
		System.out.println(res.getElements().size());
		
	}
	
	public void testService()
	{
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<Long> courses = new ArrayList<Long>();
		courses.add(11476L);
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
        Session session = dbHandler.getMiningSession();
	        
        Criteria criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
        criteria.add(Restrictions.in("aso.course.id", courses));
        	        
        @SuppressWarnings("unchecked")
        ArrayList<ICourseRatedObjectAssociation> list = (ArrayList<ICourseRatedObjectAssociation>) criteria.list();
        
        for(ICourseRatedObjectAssociation obj : list)
        {
        	res.add(obj.getRatedObject().getClass().getSimpleName());
        	res.add(obj.getRatedObject().getPrefix().toString());
        	res.add(obj.getRatedObject().getId() + "");
        	res.add(obj.getRatedObject().getTitle());
        }
        System.out.println("Starting test");
	}
	
	public void run()
	{
		System.out.println("Starting test");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		testHisto();
		System.out.println("Test finished");
	}
	
	public static void main(String[] args)
	{
		Test t = new Test();
		t.run();
	}

}
