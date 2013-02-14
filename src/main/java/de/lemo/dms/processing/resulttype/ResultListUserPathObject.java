/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserPathObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for UserPathObjec which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListUserPathObject {

	private List<UserPathObject> userPaths;

	@XmlElement
	public final String type = this.getClass().getSimpleName();

	public ResultListUserPathObject()
	{

	}

	public ResultListUserPathObject(final List<UserPathObject> userPaths)
	{
		this.userPaths = userPaths;
	}

	@XmlElement
	public List<UserPathObject> getUserPaths()
	{
		return this.userPaths;
	}

	public void setUserPaths(final List<UserPathObject> userPaths)
	{
		this.userPaths = userPaths;
	}
}
