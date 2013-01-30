/**
 * File ./main/java/de/lemo/dms/test/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.test;

import org.hibernate.Session;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;

public class Test {

	public static void gen()
	{
		final ContentGenerator conGen = new ContentGenerator();
		ServerConfiguration.getInstance().loadConfig("/lemo");
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 2, 2, 251, 0L, 500));
	}

	public static void write()
	{
		final TestDataCreatorChemgapedia ch = new TestDataCreatorChemgapedia();

		ch.getDataFromDB();
		ch.writeDataSource("c://users//s.schwarzrock//desktop//chemgaLog.log",
				"c://users//s.schwarzrock//desktop//VluGen");
	}

	public static void writeMoodle()
	{
		final TestDataCreatorMoodle mod = new TestDataCreatorMoodle();
		mod.getDataFromDB();
		mod.writeSourceDB();
	}

	public static void main(final String[] args)
	{
		Test.gen();
		// write();
		// writeMoodle();
	}
}
