/**
 * File ./src/main/java/de/lemo/dms/test/LogDurationCalculator.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/test/LogDurationCalculator.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import de.lemo.dms.db.mapping.abstractions.ILogMining;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/**
 * Calculates and sets the duration-attributes for a List of log-objects. The list "values" has not to be sorted
 * before.
 * 
 * @author Sebastian Schwarzrock
 */
public final class LogDurationCalculator {

	private LogDurationCalculator() {
	}

	/**
	 * Calculates and sets the duration-attributes for a List of log-objects. The list "values" has not to be sorted
	 * before.
	 * 
	 * @param values
	 *            List of Log-objects.
	 */
	public static void calculateDurations(final List<ILogMining> values)
	{
		Collections.sort(values);
		final HashMap<Long, ArrayList<ILogMining>> userHistories = new HashMap<Long, ArrayList<ILogMining>>();
		for (final ILogMining log : values)
		{
			log.setDuration(-1L);

			if (userHistories.get(log.getUser().getId()) == null)
			{
				final ArrayList<ILogMining> list = new ArrayList<ILogMining>();
				list.add(log);
				userHistories.put(log.getUser().getId(), list);
			}
			else
			{
				final ILogMining prevLog = userHistories.get(log.getUser().getId()).get(
						userHistories.get(log.getUser().getId()).size() - 1);
				final long d = log.getTimestamp() - prevLog.getTimestamp();
				prevLog.setDuration(d);
				userHistories.get(log.getUser().getId()).add(log);
			}
		}

		System.out.println();

	}

	/**
	 * Calculates and sets the duration-attributes for a List of generated log-objects. It
	 * adds occasional "log-outs" (logs with duration "-1"). The list "values" has not to be sorted before.
	 * 
	 * @param values
	 *            List of Log-objects.
	 */
	public static void calculateDurationsForGeneratedData(final List<ILogMining> values)
	{
		Collections.sort(values);
		final HashMap<Long, ArrayList<ILogMining>> userHistories = new HashMap<Long, ArrayList<ILogMining>>();
		for (final ILogMining log : values)
		{
			log.setDuration(-1L);
			final Random randy = new Random();

			if (userHistories.get(log.getUser().getId()) == null)
			{
				final ArrayList<ILogMining> list = new ArrayList<ILogMining>();
				list.add(log);
				userHistories.put(log.getUser().getId(), list);
			}
			else
			{
				final ILogMining prevLog = userHistories.get(log.getUser().getId()).get(
						userHistories.get(log.getUser().getId()).size() - 1);
				final long d = log.getTimestamp() - prevLog.getTimestamp();
				final int r = randy.nextInt(3);
				if (r == 0) {
					prevLog.setDuration(-1L);
				} else {
					prevLog.setDuration(d);
				}
				userHistories.get(log.getUser().getId()).add(log);
			}
		}

		System.out.println();

	}

	/**
	 * Sorts a list of log-objects and set the id-attributes accordingly.
	 * 
	 * @param values
	 *            List of Log-objects.
	 * @param offset
	 *            Offset for the ids. If the offset is "0", the oldest log-object gets the id "0".
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createIds(final List<?> values, final int offset)
	{

		Collections.sort((List<Comparable>)values);
		for (int i = 0; i < values.size(); i++)
		{
			((List<IMappingClass>) values).get(i).setId(i + offset);
		}

	}

}
