package de.lemo.dms.connectors;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
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
			sourceConf.addProperty("process_metadata", "false");
			sourceConf.addProperty("process_log_file", "true");
			
			ConnectorChemgapedia cm = new ConnectorChemgapedia();
			cm.setSourceDBConfig(sourceConf);
			
			cm.getData();
		}

		
		
	}
	
	public static void runMoodleConn()
	{
		ConnectorMoodle cm = new ConnectorMoodle();
		
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData();
	}
	
	public static void runClixConn()
	{
		ConnectorClix cc = new ConnectorClix();
		cc.getData();
	}
	
	public static void main(String[] args)
	{
		//runChemConn();
		//runClixConn();
		runMoodleConn();
	}
	
	
}
