package de.lemo.dms.connectors.chemgapedia;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.chemgapedia.fizHelper.LogReader;
import de.lemo.dms.connectors.chemgapedia.fizHelper.XMLPackageParser;
import de.lemo.dms.core.ServerConfigurationHardCoded;

public class ConnectorChemgapedia implements IConnector{

	static DBConfigObject sourceDBConf;
	static boolean filter = false;
	static boolean processVSC = false;
	static boolean processLog = false;
	static String logPath;
	static String vscPath;
	private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	
	@Override
	public boolean testConnections() {
		
		if(logPath == null)
		{
			logger.info("Connector Chemgapedia : No path for log file defined");
			return false;
		}			
		if(vscPath == null)
		{
			logger.info("Connector Chemgapedia : No path for resource metadata defined");
			return false;
		}
		File f = new File(logPath);
		if(!f.exists())
		{
			logger.info("Connector Chemgapedia : Defined Log file doesn't exist.");
			return false;
		}
		
		return true;
	}

	@Override
	public void getData() {
		
		Long starttime = System.currentTimeMillis()/1000;
		
		Long largestId = -1L;		
		if(processVSC)
		{
			XMLPackageParser x = new XMLPackageParser();
			x.readAllVlus(vscPath);
			largestId = x.saveAllToDB();
		}
		if(processLog)
		{			
			LogReader logR = new LogReader(largestId);
			logR.loadServerLogData(logPath);
			if(filter)
				
				logR.filterServerLogFile();
			logR.usersToDB();
			largestId = logR.resourceLogsToDB();
		}
		
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform("Chemgapedia");
	    ServerConfigurationHardCoded.getInstance().getDBHandler().saveToDB(config);
		ServerConfigurationHardCoded.getInstance().getDBHandler().closeConnection();
	}

	@Override
	public void updateData(long fromTimestamp) {
		Long starttime = System.currentTimeMillis()/1000;
		
		Long largestId = -1L;		
		if(processVSC)
		{
			XMLPackageParser x = new XMLPackageParser();
			x.readAllVlus(vscPath);
			largestId = x.saveAllToDB();
		}
		if(processLog)
		{			
			LogReader logR = new LogReader(largestId);
			logR.loadServerLogData(logPath);
			if(filter)
				
				logR.filterServerLogFile();
			logR.usersToDB();
			largestId = logR.resourceLogsToDB();
		}
		
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform("Chemgapedia");
	    ServerConfigurationHardCoded.getInstance().getDBHandler().saveToDB(config);
		ServerConfigurationHardCoded.getInstance().getDBHandler().closeConnection();
	}

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		
		HashMap<String, String> props = dbConf.getProperties();
		if(props.get("filter_log_file").equals("true"))
			filter = true;
		if(props.get("path.log_file") == null)
			logger.error("Connector Chemgapedia : No path for log file defined");
		else
			logPath = props.get("path.log_file");
		if(props.get("path.resource_metadata") == null)
			logger.error("Connector Chemgapedia : No path for resource metadata defined");
		else
			vscPath = props.get("path.resource_metadata");
		if(props.get("process_metadata") == "true")
			processVSC = true;
		if(props.get("process_log_file") == "true")
			processLog = true;
			
	}
}
