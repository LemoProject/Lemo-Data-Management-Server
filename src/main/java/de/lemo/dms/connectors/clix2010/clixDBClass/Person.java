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
	private String lastUpdated;

	public Person()
	{

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


	public String getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
