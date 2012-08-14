package de.lemo.dms.connectors;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle;
import de.lemo.dms.core.ServerConfigurationHardCoded;


public class Test {
	
	public static void main(String[] args)
	{
		ConnectorMoodle cm = new ConnectorMoodle();
		cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		cm.getData();
		
		//cm.setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
		
		
		//cm.setSourceDBConfig(sourceConf);
		//cm.getData();
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
