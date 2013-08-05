/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/Person.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/Person.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table Person.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "PERSON")
public class Person implements IClixMappingClass {

	private Long id;
	private String lastLoginTime;
	private String firstLoginTime;
	private String login;
	private Long gender;
	private String lastUpdated;

	public Person()
	{

	}


	@Id
	@Column(name="PERSON_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name="LASTLOGINTIME")
	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(final String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name="FIRSTLOGINTIME")
	public String getFirstLoginTime() {
		return this.firstLoginTime;
	}

	public void setFirstLoginTime(final String firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	@Column(name="LOGIN")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	@Column(name="SALUTATION_ID")
	public Long getGender() {
		return this.gender;
	}

	public void setGender(final Long gender) {
		this.gender = gender;
	}


	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
