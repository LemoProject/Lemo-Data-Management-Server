/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/ResultListLog.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListLog.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import de.lemo.dms.db.mapping.abstractions.ILog;

/**
 * represents a list for Log objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListLog {

	@XmlElement
	private final JSONArray logs = new JSONArray();
	private Logger logger = Logger.getLogger(this.getClass());
	
	public ResultListLog() {
		/* JAXB no-arg default constructor */
	}

	public ResultListLog(final List<ILog> dbLog) {
		try {
			for (final ILog log : dbLog) {
				final JSONObject logJSON = new JSONObject();
				logJSON.put("id", log.getId());
				logJSON.put("time", log.getTimestamp());
				logJSON.put("action", "");
				logJSON.put("course", log.getCourse());
				logJSON.put("user", log.getUser());

				this.logs.put(logJSON);
			}
		} catch (final JSONException e) {
			logger.error(e.getMessage());
		}
	}
}
