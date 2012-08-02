package de.lemo.dms.test;

import org.hibernate.Session;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;

public class Test {

	
	public static void main(String[] args)
	{
		ContentGenerator conGen = new ContentGenerator();
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
        dbHandler.saveCollectionToDB(conGen.generateMiningDB(5, 2, 2, 1293840000L, 1000));
	}
}
