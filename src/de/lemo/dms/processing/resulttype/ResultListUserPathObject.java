package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserPathObject {

	private List<UserPathObject> userPaths;
	
	
	public ResultListUserPathObject()
	{
		
	}
	
	public ResultListUserPathObject(List<UserPathObject> userPaths)
	{
		this.userPaths = userPaths;
	}
	
	@XmlElement
	public List<UserPathObject> getUserPaths()
	{
		return this.userPaths;
	}
	
	public void setUserPaths(List<UserPathObject> userPaths)
	{
		this.userPaths = userPaths;
	}
}
