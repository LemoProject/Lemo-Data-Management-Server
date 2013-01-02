package de.lemo.dms.connectors.chemgapedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.db.miningDBclass.PlatformMining;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.chemgapedia.fizHelper.LogReader;
import de.lemo.dms.connectors.chemgapedia.fizHelper.XMLPackageParser;
import de.lemo.dms.core.ServerConfigurationHardCoded;

public class ConnectorChemgapedia implements IConnector{

	private boolean filter = false;
	private boolean processVSC = false;
	private boolean processLog = false;
	private String logPath;
	private String vscPath;
	private Logger logger = Logger.getLogger(getClass());
	
	
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

	public void getData(String platformName) {
		
		Long starttime = System.currentTimeMillis()/1000;
		PlatformMining platform = null;
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = dbHandler.getMiningSession();
		
		Query old_platform = session.createQuery("from PlatformMining x order by x.id asc");
        ArrayList<PlatformMining> l = (ArrayList<PlatformMining>) old_platform.list();
		
		Long largestId = -1L;	
		if(processVSC || processLog)
		{
			Long pid = 0L;
			Long pref = 10L;
			
			for(PlatformMining p : l)
			{
				if( p.getId() > pid)
	        		pid = p.getId();
				if( p.getPrefix() > pref)
	        		pref = p.getPrefix();
				
	        	if(p.getType().equals("Chemgapedia") && p.getName().equals(platformName))
	        	{        		
	        		platform = p;
	        	}
			}
			if(platform == null)
			{
				platform = new PlatformMining();
				platform.setId(pid + 1);
				platform.setType("Chemgapedia");
				platform.setName(platformName);
				platform.setPrefix(pref + 1);			
			}
			
			session.close();
			
			if(processVSC)
			{
				XMLPackageParser x = new XMLPackageParser(platform);
				x.readAllVlus(vscPath);
				largestId = x.saveAllToDB();
			}
			if(processLog)
			{			
				LogReader logR = new LogReader(platform, largestId);
				logR.loadServerLogData(logPath);
				if(filter)
					
					logR.filterServerLogFile();
				
				largestId = logR.save();
			}
			
			Long endtime = System.currentTimeMillis()/1000;
			ConfigMining config = new ConfigMining();
		    config.setLastmodified(System.currentTimeMillis());
		    config.setElapsed_time((endtime) - (starttime));	
		    config.setLargestId(largestId);
		    config.setPlatform(platform.getId());
		    
		    session = dbHandler.getMiningSession();
		    dbHandler.saveToDB(session, config);
		    dbHandler.saveToDB(session, platform);
		    dbHandler.closeSession(session);
		}
	}

	public void updateData(String platformName, long fromTimestamp) {
		Long starttime = System.currentTimeMillis()/1000;
PlatformMining platform = null;
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = dbHandler.getMiningSession();
		
		Query old_platform = session.createQuery("from PlatformMining x order by x.id asc");
        ArrayList<PlatformMining> l = (ArrayList<PlatformMining>) old_platform.list();
		
		Long largestId = -1L;	
		if(processVSC || processLog)
		{
			Long pid = 0L;
			Long pref = 10L;
			
			for(PlatformMining p : l)
			{
				if( p.getId() > pid)
	        		pid = p.getId();
				if( p.getPrefix() > pref)
	        		pref = p.getPrefix();
				
	        	if(p.getType().equals("Chemgapedia") && p.getName().equals(platformName))
	        	{        		
	        		platform = p;
	        	}
			}
			if(platform == null)
			{
				platform = new PlatformMining();
				platform.setId(pid + 1);
				platform.setType("Chemgapedia");
				platform.setName(platformName);
				platform.setPrefix(pref + 1);			
			}
			
			session.close();		
			if(processVSC)
			{
				XMLPackageParser x = new XMLPackageParser(platform);
				x.readAllVlus(vscPath);
				largestId = x.saveAllToDB();
			}
			if(processLog)
			{			
				LogReader logR = new LogReader(platform, largestId);
				
				
				logR.loadServerLogData(logPath);
				if(filter)
					
					logR.filterServerLogFile();
			
				largestId = logR.save();
			}
			
			Long endtime = System.currentTimeMillis()/1000;
			ConfigMining config = new ConfigMining();
		    config.setLastmodified(System.currentTimeMillis());
		    config.setElapsed_time((endtime) - (starttime));	
		    config.setLargestId(largestId);
		    config.setPlatform(platform.getId());
		    
		    dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		    session = dbHandler.getMiningSession();
	        dbHandler.saveToDB(session, config);
	        dbHandler.closeSession(session);
		}
	}

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		
		HashMap<String, String> props = dbConf.getProperties();
		
		if(props.get("filter_log_file").equals("true"))
			filter = true;
		else
			filter = false;
		
		if(props.get("path.log_file") == null)
			logger.error("Connector Chemgapedia : No path for log file defined");
		else
			logPath = props.get("path.log_file");
		

		
		if(props.get("process_metadata") == "true")
			processVSC = true;
		else
			processVSC = false;
		
		if(props.get("process_log_file") == "true")
			processLog = true;
		else
			processLog = false;
		
		if(props.get("path.resource_metadata") == null && processVSC)
			logger.error("Connector Chemgapedia : No path for resource metadata defined");
		else
			vscPath = props.get("path.resource_metadata");
			
	}
}
