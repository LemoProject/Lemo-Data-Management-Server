package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsViger;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.connectors.clix2010.HibernateUtil;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;
import de.lemo.dms.core.ServerConfigurationHardCoded;

/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 *
 */
public class Test {
	
	/**
	 * Tests the Chemgapedia-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public static void runChemConn()
	{
		for(int i = 0 ; i < 5; i++)
		{
			DBConfigObject sourceConf = new DBConfigObject();
			sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_"+i+".log");
			sourceConf.addProperty("path.resource_metadata", "C:\\Users\\s.schwarzrock\\Desktop\\vsc");
			sourceConf.addProperty("filter_log_file", "true");
			if(i == 0)
				sourceConf.addProperty("process_metadata", "true");
			else
				sourceConf.addProperty("process_metadata", "false");
			sourceConf.addProperty("process_log_file", "true");
			
			ConnectorChemgapedia cm = new ConnectorChemgapedia();
			cm.setSourceDBConfig(sourceConf);
			cm.getData("Chemgapedia(FIZ)");
		}
	}
	
	/**
	 * Tests the Moodle(1.9)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public static void runMoodleConn()
	{
		ConnectorMoodle cm = new ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData("Moodle(Beuth)");
		//cm.updateData("Moodle(Beuth)", 1338000000);
	}
	
	/**
	 * Tests the Moodle(2.3)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public static void runMoodle23Conn()
	{
		de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle cm = new de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData("Moodle 2.3(Beuth)");
		//cm.updateData("Moodle 2.3(Beuth)", 1338000000);
	}
	
	/**
	 * Tests the Clix(2010)-connector. Configurations have to be altered accordingly.
	 * 
	 */
	public static void runClixConn()
	{
		ConnectorClix cc = new ConnectorClix();
		cc.getData("Clix(HTW)");
	}
	
	public static void test()
	{
		Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
        session.clear();
		
		Query pers = session.createQuery("from Person x order by x.id asc");
        List<?> person = pers.list();	        
        System.out.println("Person tables: " + person.size()); 
	}
	
	public static void calculateMeichsner()
	{
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
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
	
	public static void test2()
	{
		QFrequentPathsViger qca = new QFrequentPathsViger();
		List<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		Long startTime = 1323385200L;
		Long endTime = 1335736800L;
		ArrayList<String> types = new ArrayList<String>();
		//types.add("resource");
	//  qca.compute(courses, users, types, minLength, maxLength, minInterval, maxInterval, minWholeInterval, maxWholeInterval, minSup, sessionWise, startTime, endTime)
		qca.compute(courses, new ArrayList<Long>(), types, null, null, 0d, 5d, 0d, Double.MAX_VALUE, 0.5, false, startTime, endTime);
	}
	
	public static void test3()
	{
		QFrequentPathsBIDE qca = new QFrequentPathsBIDE();
		List<Long> courses = new ArrayList<Long>();
		courses.add(112200L);
		Long startTime = 1323385200L;
		Long endTime = 1335736800L;
		ArrayList<String> types = new ArrayList<String>();
		//types.add("resource");
		qca.compute(courses, new ArrayList<Long>(), types, null, null, 0.5, false, startTime, endTime);
	}
	
	public static void run()
	{
		System.out.println("Starting test");
		test3();
		test2();
		System.out.println("Test finished");
	}

	
	
}
