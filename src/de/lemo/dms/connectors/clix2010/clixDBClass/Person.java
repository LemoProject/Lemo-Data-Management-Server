package de.lemo.dms.connectors.clix2010.clixDBClass;

public class Person {
	
	private Long id;
	private String lastLoginTime;
	private String firstLoginTime;
	
	public Person()
	{ 
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
