package de.lemo.dms.connectors.clix2010.clixDBClass;

public class Person {
	
	private long id;
	private String lastLoginTime;
	private String firstLoginTime;
	
	public Person()
	{ 
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getFirstLoginTime() {
		return firstLoginTime;
	}
	public void setFirstLoginTime(String firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}
	
	

}
