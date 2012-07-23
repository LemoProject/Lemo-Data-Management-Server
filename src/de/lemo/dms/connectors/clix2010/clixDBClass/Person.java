package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class Person  implements IClixMappingClass{
	
	private Long id;
	private String lastLoginTime;
	private String firstLoginTime;
	private String login;
	private long sex;
	


	public Person()
	{ 
		
	}
	
	public String getString()
	{
		return "Person�$"
				+this.id+"�$"
				+this.getFirstLoginTime()+"�$"
				+this.getLastLoginTime()+"�$"
				+this.getLogin()+"�$"
				+this.getSex();
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
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public long getSex() {
		return sex;
	}

	public void setSex(long sex) {
		this.sex = sex;
	}
	
	

}
