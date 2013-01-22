package de.lemo.dms.connectors.chemgapedia;

import java.io.File;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.connectors.chemgapedia.fizHelper.LogReader;
import de.lemo.dms.connectors.chemgapedia.fizHelper.XMLPackageParser;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ConfigMining;

/**
 * Connector implementation for the platform Chemgapedia
 * 
 * @author s.schwarzrock
 *
 */
public class ConnectorChemgapedia extends AbstractConnector {

    private static final String PATH_LOG_FILE = "lemo.log_file_path";
    private static final String PATH_RESOURCE_METADATA = "lemo.resource_metadata_path";
    private static final String PROCESS_LOG_FILE = "lemo.process_log_file";
    private static final String PROCESS_METADATA = "lemo.process_metadata";
    private static final String FILTER_LOG_FILE = "lemo.filter_log_file";

    private boolean filter;
    private boolean processVSC;
    private boolean processLog;
    private String logPath;
    private String vscPath;
    private Logger logger = Logger.getLogger(getClass());

    public ConnectorChemgapedia(DBConfigObject config) {
        HashMap<String, String> props = config.getProperties();

        // required
        logPath = props.get(PATH_LOG_FILE);
        if(props.get(PATH_LOG_FILE) == null)
            logger.error("Connector Chemgapedia : No path for log file defined");

        // optional
        filter = props.containsKey(FILTER_LOG_FILE) && props.get(FILTER_LOG_FILE).toLowerCase().equals("true");
        processVSC = props.containsKey(PROCESS_METADATA) && props.get(PROCESS_METADATA).toLowerCase().equals("true");
        processLog = props.containsKey(PROCESS_LOG_FILE) && props.get(PROCESS_LOG_FILE).toLowerCase().equals("true");

        // conditionally required
        vscPath = props.get(PATH_RESOURCE_METADATA);
        if(vscPath == null && processVSC)
            logger.error("Connector Chemgapedia : No path for resource metadata defined");

    }

    @Override
    public boolean testConnections() {

        if(logPath == null)
        {
            logger.error("Connector Chemgapedia : No path for log file defined");
            return false;
        }
        if(vscPath == null)
        {
            logger.error("Connector Chemgapedia : No path for resource metadata defined");
            return false;
        }
        File f = new File(logPath);
        if(!f.exists())
        {
            logger.error("Connector Chemgapedia : Defined Log file doesn't exist.");
            return false;
        }

        return true;
    }

    @Override
    public void getData() {
        Long starttime = System.currentTimeMillis() / 1000;

        if(processVSC || processLog)
        {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
            Session session = dbHandler.getMiningSession();
            
            if(processVSC)
            {
                XMLPackageParser x = new XMLPackageParser(this);
                x.readAllVlus(vscPath);
				x.saveAllToDB();
				x.clearMaps();
            }
            if(processLog)
            {
                LogReader logR = new LogReader(this, session);
				logR.loadServerLogData(logPath, 0L, filter, session);
				logR.save(session);
				logR.clearMaps();
            }

            Long endtime = System.currentTimeMillis() / 1000;
            ConfigMining config = new ConfigMining();
            config.setLastModifiedLong(System.currentTimeMillis());
            config.setElapsedTime((endtime) - (starttime));
		    config.setDatabaseModel("1.2");
            config.setPlatform(getPlatformId());
           
            session = dbHandler.getMiningSession();
            dbHandler.saveToDB(session, config);
            dbHandler.closeSession(session);
        }
    }

    @Override
    public void updateData(long fromTimestamp) {
        Long starttime = System.currentTimeMillis() / 1000;

        if(processVSC || processLog)
        {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
            Session session = dbHandler.getMiningSession();

            if(processVSC)
            {
                XMLPackageParser x = new XMLPackageParser(this);
                x.readAllVlus(vscPath);
				x.saveAllToDB();
				x.clearMaps();
            }
            if(processLog)
            {
                LogReader logR = new LogReader(this, session);
				logR.loadServerLogData(logPath, 0L, filter, session);
				logR.save(session);
				logR.clearMaps();				
			}
			
            Long endtime = System.currentTimeMillis() / 1000;
            ConfigMining config = new ConfigMining();
            config.setLastModifiedLong(System.currentTimeMillis());
            config.setElapsedTime((endtime) - (starttime));
		    config.setDatabaseModel("1.2");
            config.setPlatform(getPlatformId());


            dbHandler.saveToDB(session, config);
            dbHandler.closeSession(session);
        }
    }

}
