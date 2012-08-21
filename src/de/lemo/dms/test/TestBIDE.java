package de.lemo.dms.test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;

public class TestBIDE {

	
	
	
	public static void main(String[] args)
	{
		try{
		Clock c = new Clock();
        
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		Session session =  dbHandler.getSession();
		session.close();
		for(int i = 1; i < 19; i++)
		{
			FileWriter out = new FileWriter("c:\\users\\s.schwarzrock\\desktop\\Bide\\120820_bide_Course " + i + ".txt");
	    	PrintWriter pout = new PrintWriter(out);
			pout.println(i + "\t===============================================================");
			for(int j = 1; j < 4; j++)
			{
				
				c.getAndReset();
				QFrequentPathsBIDE fp = new QFrequentPathsBIDE();
				List<Long> courseIds = new ArrayList<Long>();
				courseIds.add(Long.valueOf(i));
				List<Long> userIds = new ArrayList<Long>();
				double minSup = 1d / j;
				boolean heedSessions = false;
				Long startTime = 0L;
				Long endTime =   1500000000L;
				fp.compute(courseIds, userIds, minSup, heedSessions, startTime, endTime);
				//pout.println("\nMinSup: " + minSup + "\t" + c.getAndReset());
			}
			pout.close();
		}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
