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
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

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

	public ResultListLog(final List<ILogMining> dbLog) {
		try {
			for (final ILogMining log : dbLog) {
				final JSONObject logJSON = new JSONObject();
				logJSON.put("id", log.getId());
				logJSON.put("time", log.getTimestamp());
				logJSON.put("action", log.getAction());
				logJSON.put("course", log.getCourse());
				logJSON.put("user", log.getUser());

				this.logs.put(logJSON);
			}
		} catch (final JSONException e) {
			logger.error(e.getMessage());
		}
	}
}
