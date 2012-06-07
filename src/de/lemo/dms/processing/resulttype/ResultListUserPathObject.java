package de.lemo.dms.processing.resulttype;

import java.util.List;

public class ResultListUserPathObject {
	
	private List<UserLogObject> userPaths;
	
	public ResultListUserPathObject()
	{
		
	}
	
	public ResultListUserPathObject(List<UserLogObject> userPaths)
	{
		this.setUserPaths(userPaths);
	}

	public List<UserLogObject> getUserPaths() {
		return userPaths;
	}

	public void setUserPaths(List<UserLogObject> userPaths) {
		this.userPaths = userPaths;
	}

}
