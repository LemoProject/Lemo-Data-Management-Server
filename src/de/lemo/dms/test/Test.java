package de.lemo.dms.test;


import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;

public class Test {

	
	
	public static void gen()
	{
		ContentGenerator conGen = new ContentGenerator();
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
        dbHandler.saveCollectionToDB(conGen.generateMiningDB(1, 1, 27, 1293840000L, 1000));
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
		gen();
		//write();
		//writeMoodle();
	}
}
