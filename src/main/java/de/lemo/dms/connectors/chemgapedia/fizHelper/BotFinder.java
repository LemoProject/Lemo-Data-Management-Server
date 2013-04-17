/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/fizHelper/BotFinder.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class offers some functions for the detection of web-crawlers.
 * 
 * @author s.schwarzrock
 */
public class BotFinder {

	/**
	 * Takes all requests of a user and divides the sequence into several sessions according to web-usyge heuristics.
	 * 
	 * @param log
	 * @return
	 */
	public List<ArrayList<LogObject>> sessionize(final List<LogObject> log)
	{
		final ArrayList<ArrayList<LogObject>> sessions = new ArrayList<ArrayList<LogObject>>();
		final ArrayList<LogObject> currentSession = new ArrayList<LogObject>();
		final HashSet<String> previousPages = new HashSet<String>();
		for (int i = 0; i < log.size(); i++)
		{
			final LogObject l = log.get(i);
			if ((l.getReferrer().equals("-")) || !previousPages.contains(l.getReferrer()))
			{
				if (currentSession.size() > 0) {
					sessions.add(currentSession);
				}
				currentSession.clear();
				previousPages.clear();
			}
			currentSession.add(l);
			previousPages.add(l.getUrl());
		}
		return sessions;
	}

	/**
	 * @param log
	 *            Logfile containing all views the user did.
	 * @param chunkLimit
	 *            Maximal number of views within a second.
	 * @return List with all suspicious chunks.
	 */
	public List<Integer> checkFastOnes(final List<LogObject> log, final int chunkLimit)
	{
		final ArrayList<Integer> chunks = new ArrayList<Integer>();
		Collections.sort(log);
		long lastTime = 0;
		int chunksize = 0;
		for (int i = 0; i < log.size(); i++)
		{
			if (i > 0)
			{
				if (log.get(i).getTime() == lastTime)
				{
					chunksize++;
				}
				else
				{
					if (chunksize > chunkLimit) {
						chunks.add(chunksize);
					}
					chunksize = 0;
				}
			}
			lastTime = log.get(i).getTime();
		}
		return chunks;
	}

	/**
	 * @param log
	 *            Logfile containing all views the user did.
	 * @param chunkLimit
	 *            Maximal number of occurrences of a specific time span in relation to the total number of views by this
	 *            user.
	 * @return List containing all suspicious chunks.
	 */
	public int checkPeriods(final List<LogObject> log, final int chunkLimit)
	{
		Collections.sort(log);
		// Skip if there was just one view
		if (log.size() < chunkLimit) {
			return 0;
		}

		// Final List
		int chunks = 0;
		// List of time spans that already occurred
		final HashMap<Integer, Integer> span = new HashMap<Integer, Integer>();
		// List of the number of occurrences of known time spans

		long lastT = 0;
		for (int i = 0; i < log.size(); i++)
		{
			if (i > 0)
			{
				final Integer l = (int) (log.get(i).getTime() - lastT);
				if (span.get(l) != null)
				{
					final int c = span.get(l) + 1;
					span.put(l, c);
				} else {
					span.put(l, 1);
				}
				lastT = log.get(i).getTime();
			}
		}
		final ArrayList<Integer> counts = new ArrayList<Integer>(span.values());
		for (int i = 0; i < counts.size(); i++) {
			if (counts.get(i) > (log.size() / chunkLimit)) {
				chunks++;
			}
		}
		return chunks;
	}

	/**
	 * @param log
	 *            Logfile containing all views the user did.
	 * @param chunkLimit
	 *            Maximal number of views performed on a single page.
	 * @return number of requests of the most frequent requested page
	 */
	public int checkForRepetitions(final List<LogObject> log, final int chunkLimit) {

		final Map<String, Integer> temp = new Hashtable<String, Integer>();
		for (int i = 0; i < log.size(); i++)
		{
			if (temp.containsKey(log.get(i).getUrl()))
			{
				final int d = temp.get(log.get(i).getUrl()) + 1;
				temp.put(log.get(i).getUrl(), d);
			} else {
				temp.put(log.get(i).getUrl(), 1);
			}
		}
		final Set<String> set = temp.keySet();
		final Iterator<String> it = set.iterator();
		String ke;
		int max = 0;
		while (it.hasNext())
		{
			ke = it.next();
			if ((temp.get(ke) > chunkLimit) && (temp.get(ke) > max)) {
				max = temp.get(ke);
			}
		}
		return max;
	}

}
