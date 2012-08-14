package de.lemo.dms.test;


import org.hibernate.Session;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;

public class Test {

	
	
	public static void gen()
	{
		ContentGenerator conGen = new ContentGenerator();
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		Session session = dbHandler.getMiningSession();
        dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 2, 2, 1293840000L, 1000));
        dbHandler.closeSession(session);
	}
	
	public static void write()
	{
		TestDataCreatorChemgapedia ch = new TestDataCreatorChemgapedia();
		
		ch.getDataFromDB();
		ch.writeDataSource("c://users//s.schwarzrock//desktop//chemgaLog.log", "c://users//s.schwarzrock//desktop//VluGen");
	}
	
	public static void writeMoodle()
	{
		TestDataCreatorMoodle mod = new TestDataCreatorMoodle();
		mod.getDataFromDB();
		mod.writeSourceDB();
	}
	
	
	public static void main(String[] args)
	{
		//gen();
		//write();
		writeMoodle();
	}
}
