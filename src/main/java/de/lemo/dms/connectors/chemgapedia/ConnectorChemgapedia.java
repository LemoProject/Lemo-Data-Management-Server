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

public class ConnectorChemgapedia extends AbstractConnector {

    private boolean filter = false;
    private boolean processVSC = false;
    private boolean processLog = false;
    private String logPath;
    private String vscPath;
    private Logger logger = Logger.getLogger(getClass());

    public ConnectorChemgapedia(DBConfigObject config) {
        HashMap<String, String> props = config.getProperties();

        filter = props.get("filter_log_file") != null && props.get("filter_log_file").equals("true");
        processVSC = props.get("process_metadata") != null && props.get("process_metadata").equals("true");
        processLog = props.get("process_log_file") != null && props.get("process_log_file").equals("true");

        if(props.get("path.log_file") == null)
            logger.error("Connector Chemgapedia : No path for log file defined");
        else
            logPath = props.get("path.log_file");

        if(props.get("path.resource_metadata") == null && processVSC)
            logger.error("Connector Chemgapedia : No path for resource metadata defined");
        else
            vscPath = props.get("path.resource_metadata");

    }

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
        Long starttime = System.currentTimeMillis() / 1000;

        IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

        Long largestId = -1L;
        if(processVSC || processLog)
        {

            if(processVSC)
            {
                XMLPackageParser x = new XMLPackageParser(getPlatformId());
                x.readAllVlus(vscPath);
                largestId = x.saveAllToDB();
            }
            if(processLog)
            {
                LogReader logR = new LogReader(getPlatformId(), largestId);
                logR.loadServerLogData(logPath);
                if(filter)
                    logR.filterServerLogFile();

                largestId = logR.save();
            }

            Long endtime = System.currentTimeMillis() / 1000;
            ConfigMining config = new ConfigMining();
            config.setLastmodified(System.currentTimeMillis());
            config.setElapsed_time((endtime) - (starttime));
            config.setLargestId(largestId);
            config.setPlatform(getPlatformId());

            Session session = dbHandler.getMiningSession();
            dbHandler.saveToDB(session, config);
            dbHandler.closeSession(session);
        }
    }

    @Override
    public void updateData(long fromTimestamp) {
        Long starttime = System.currentTimeMillis() / 1000;

        IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

        Long largestId = -1L;
        if(processVSC || processLog)
        {

            if(processVSC)
            {
                XMLPackageParser x = new XMLPackageParser(getPlatformId());
                x.readAllVlus(vscPath);
                largestId = x.saveAllToDB();
            }
            if(processLog)
            {
                LogReader logR = new LogReader(getPlatformId(), largestId);

                logR.loadServerLogData(logPath);
                if(filter)
                    logR.filterServerLogFile();

                largestId = logR.save();
            }

            Long endtime = System.currentTimeMillis() / 1000;
            ConfigMining config = new ConfigMining();
            config.setLastmodified(System.currentTimeMillis());
            config.setElapsed_time((endtime) - (starttime));
            config.setLargestId(largestId);
            config.setPlatform(getPlatformId());

            dbHandler = ServerConfiguration.getInstance().getDBHandler();
            Session session = dbHandler.getMiningSession();
            dbHandler.saveToDB(session, config);
            dbHandler.closeSession(session);
        }
    }

}
