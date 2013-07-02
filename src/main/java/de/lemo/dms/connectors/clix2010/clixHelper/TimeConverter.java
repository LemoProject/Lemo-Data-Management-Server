/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixHelper/TimeConverter.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixHelper/TimeConverter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple utility-class for the conversion of Clix-timestamps to posix timestamps
 * 
 * @author s.schwarzrock
 */
public final class TimeConverter {

	private static final int THOU = 1000;

	private TimeConverter() {

	}

	/**
	 * Converts a Clix-timestamp to a posix-timestamp.
	 * 
	 * @param date
	 *            String-representation of a date within Clix.
	 * @return Posix-timestamp of the date.
	 */
	public static Long getTimestamp(final String date)
	{
		Long t = 0L;
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			final Date d = sdf.parse(date);
			t = d.getTime() / THOU;
			if(d.getTime() % THOU > 499)
			{
				t++;
			}

		} catch (final Exception e)
		{
			return t;
		}
		return t;
	}

	/**
	 * Converts a Posix-timestamp to a Clix-timestamp.
	 * 
	 * @param date
	 *            Posix-timestamp
	 * @return String-representation of a date within Clix
	 */
	public static String getStringRepresentation(final Long date)
	{
		String s = "";

		s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date * THOU);

		return s;
	}
}
