/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/Person.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table Person.
 * 
 * @author S.Schwarzrock
 *
 */
public class Person implements IClixMappingClass {

	private Long id;
	private String lastLoginTime;
	private String firstLoginTime;
	private String login;
	private Long gender;

	public Person()
	{

	}

	public String getString()
	{
		return "Person$$$"
				+ this.id + "$$$"
				+ this.getFirstLoginTime() + "$$$"
				+ this.getLastLoginTime() + "$$$"
				+ this.getLogin() + "$$$"
				+ this.getGender();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(final String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getFirstLoginTime() {
		return this.firstLoginTime;
	}

	public void setFirstLoginTime(final String firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public Long getGender() {
		return this.gender;
	}

	public void setGender(final Long gender) {
		this.gender = gender;
	}

}
