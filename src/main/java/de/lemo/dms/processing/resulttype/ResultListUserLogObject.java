/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserLogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for LogObjec which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListUserLogObject {

	private List<UserLogObject> userLogs;

	public ResultListUserLogObject()
	{

	}

	public ResultListUserLogObject(final List<UserLogObject> userLogs)
	{
		this.setUserLogs(userLogs);
	}

	@XmlElement
	public List<UserLogObject> getUserLogs() {
		return this.userLogs;
	}

	public void setUserLogs(final List<UserLogObject> userLogs) {
		this.userLogs = userLogs;
	}

}
