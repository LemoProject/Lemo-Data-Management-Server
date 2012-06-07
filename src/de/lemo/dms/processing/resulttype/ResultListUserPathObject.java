package de.lemo.dms.processing.resulttype;

import java.util.List;

public class ResultListUserPathObject {
	
	private List<UserPathObject> userPaths;
	
	public ResultListUserPathObject()
	{
		
	}
	
	public ResultListUserPathObject(List<UserPathObject> userPaths)
	{
		this.setUserPaths(userPaths);
	}

	public List<UserPathObject> getUserPaths() {
		return userPaths;
	}

	public void setUserPaths(List<UserPathObject> userPaths) {
		this.userPaths = userPaths;
	}

}
