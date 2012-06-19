package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserLogObject {
	
	private List<UserLogObject> userLogs;
	
	public ResultListUserLogObject()
	{
		
	}
	
	public ResultListUserLogObject(List<UserLogObject> userLogs)
	{
		this.setUserLogs(userLogs);
	}

	@XmlElement
	public List<UserLogObject> getUserLogs() {
		return userLogs;
	}

	public void setUserLogs(List<UserLogObject> userLogs) {
		this.userLogs = userLogs;
	}

}
