package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * This class offers some functions for the detection of web-crawlers.
 * 
 * @author s.schwarzrock
 *
 */
public class BotFinder {
	
	
	/**
	 * Takes all requests of a user and divides the sequence into several sessions according to web-usyge heuristics.
	 * 
	 * @param log	
	 * @return
	 */
	public ArrayList<ArrayList<LogObject>> sessionize(ArrayList<LogObject> log)
	{
		ArrayList<ArrayList<LogObject>> sessions = new ArrayList<ArrayList<LogObject>>();
		ArrayList<LogObject> currentSession = new ArrayList<LogObject>();
		HashSet<String> previousPages = new HashSet<String>(); 
		for(int i = 0 ; i < log.size(); i++)
		{
			LogObject l = log.get(i);
			if(l.getReferrer() == "-" || !previousPages.contains(l.getReferrer()))
			{
					if(currentSession.size() > 0)
						sessions.add(currentSession);
					currentSession.clear();
					previousPages.clear();
			}
			currentSession.add(l);
			previousPages.add(l.getUrl());
		}		
		return sessions;
	}

	
	/**
	 * 
	 * @param log Logfile containing all views the user did.
	 * @param chunkLimit Maximal number of views within a second.
	 * @return	List with all suspicious chunks.
	 */
	public ArrayList<Integer> checkFastOnes(ArrayList<LogObject> log, int chunkLimit)
	{
		ArrayList<Integer> chunks = new ArrayList<Integer>();
		Collections.sort(log);
		long lastTime = 0;
		int chunksize = 0;
		for(int i = 0 ; i < log.size(); i++)
		{
			if(i > 0)
			{
				if(log.get(i).getTime() == lastTime)
				{
					chunksize++;
				}
				else
				{
					if(chunksize > chunkLimit)
						chunks.add(chunksize);
					chunksize = 0;		
				}
			}
			lastTime = log.get(i).getTime();
		}
		return chunks;
	}
	
	/**
	 * 
	 * @param log Logfile containing all views the user did.
	 * @param chunkLimit Maximal number of occurrences of a specific time span in relation to the total number of views by this user.
	 * @return	List containing all suspicious chunks.
	 */
	public int checkPeriods(ArrayList<LogObject> log, int chunkLimit)
	{
		Collections.sort(log);
		//Skip if there was just one view
		if(log.size() < chunkLimit)
			return 0;
				
		//Final List 
		int chunks = 0;
		//List of time spans that already occurred
		HashMap<Integer, Integer> span = new HashMap<Integer, Integer>();
		//List of the number of occurrences of known time spans
		
		
		long lastT = 0;
		for(int i = 0; i < log.size(); i++)
		{
			if(i > 0)
			{
				Integer l = (int)(log.get(i).getTime() - lastT);
				if(span.get(l) != null)
				{
					int c = span.get(l) + 1;
					span.put(l, c);
				}
				else
					span.put(l,  1);
				lastT = log.get(i).getTime();
			}
		}
		ArrayList<Integer> counts = new ArrayList<Integer>(span.values());
		for(int i = 0; i < counts.size(); i++)
			if(counts.get(i) > log.size()/chunkLimit)
				chunks++;
		return chunks;
	}
	
	/**
	 * 
	 * @param log Logfile containing all views the user did.
	 * @param chunkLimit Maximal number of views performed on a single page.
	 * @return number of requests of the most frequent requested page
	 */
	public int checkForRepetitions(ArrayList<LogObject> log, int chunkLimit){
				
		Hashtable<String, Integer> temp = new Hashtable<String, Integer>();
		for(int i = 0; i < log.size(); i++)
		{
			if(temp.containsKey(log.get(i).getUrl()))
			{
				int d = temp.get(log.get(i).getUrl()) + 1;
				temp.put(log.get(i).getUrl(), d);
			}
			else
				temp.put(log.get(i).getUrl(), 1);
		}
		Set<String> set = temp.keySet();
		Iterator<String> it = set.iterator();
		String ke;
		int max = 0;
		while(it.hasNext())
		{
			ke = it.next();
			if(temp.get(ke) > chunkLimit && temp.get(ke) > max)
				max = temp.get(ke);
		}
		return max;
	}
	
}
