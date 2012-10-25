package de.lemo.dms.connectors;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.connectors.clix2010.HibernateUtil;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;
import de.lemo.dms.core.ServerConfigurationHardCoded;


public class Test {
	
	
	public static void runChemConn()
	{
		for(int i = 0 ; i < 5; i++)
		{
			DBConfigObject sourceConf = new DBConfigObject();
			sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_"+i+".log");
			sourceConf.addProperty("path.resource_metadata", "C:\\Users\\s.schwarzrock\\Desktop\\vsc");
			sourceConf.addProperty("filter_log_file", "true");
			sourceConf.addProperty("process_metadata", "true");
			sourceConf.addProperty("process_log_file", "true");
			
			ConnectorChemgapedia cm = new ConnectorChemgapedia();
			cm.setSourceDBConfig(sourceConf);
			
			
			cm.getData("Chemgapedia(FIZ)");
		}

		
		
	}
	
	public static void runMoodleConn()
	{
		ConnectorMoodle cm = new ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		//cm.getData("Moodle(Beuth)");
		cm.updateData("Moodle(Beuth)", 1338853158);
	}
	
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
	
	public static void run()
	{
		runChemConn();
	}
	
	public static void main(String[] args)
	{
		//test();
		//runChemConn();
		//runClixConn();
		//runMoodleConn();
	}
	
	
}
