package de.lemo.dms.connectors;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;
import de.lemo.dms.core.ServerConfigurationHardCoded;


public class Test {
	
	public static void main(String[] args)
	{
		DBConfigObject sourceConf = new DBConfigObject();

		sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\chemgaLog.log");
		sourceConf.addProperty("path.resource_metadata", "C:\\Users\\s.schwarzrock\\Desktop\\VluGen");
		sourceConf.addProperty("filter_log_file", "false");
		sourceConf.addProperty("process_metadata", "true");
		sourceConf.addProperty("process_log_file", "true");
		
		ConnectorChemgapedia cm = new ConnectorChemgapedia();
			
		
		//ConnectorClix cc = new ConnectorClix();
		
		
		//cc.updateData(1300000000L);
		//cc.getData();
		
		//ConnectorMoodle cm = new ConnectorMoodle();
		
		//cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		
		//cm.getData();
		
		//cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		
		
		cm.setSourceDBConfig(sourceConf);
		cm.getData();
		/*sourceConf.addProperty("process_metadata", "false");
		sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_1.log");
		cm.setSourceDBConfig(sourceConf);
		cm.getData();
		sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_2.log");
		cm.setSourceDBConfig(sourceConf);
		cm.getData();
		sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_3.log");
		cm.setSourceDBConfig(sourceConf);
		cm.getData();
		sourceConf.addProperty("path.log_file", "C:\\Users\\s.schwarzrock\\Desktop\\120614\\120614_lemo_4.log");
		cm.setSourceDBConfig(sourceConf);
		cm.getData();*/
	}
	
	
}
