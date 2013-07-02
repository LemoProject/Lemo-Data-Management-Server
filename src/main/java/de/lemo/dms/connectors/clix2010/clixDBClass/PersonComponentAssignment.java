/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignment.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignment.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table PersonComponentAssignment.
 * 
 * @author S.Schwarzrock
 *
 */
public class PersonComponentAssignment implements IClixMappingClass {

	private PersonComponentAssignmentPK id;
	private Long component;
	private Long person;
	private Long context;
	private String firstEntered;

	public String getFirstEntered() {
		return this.firstEntered;
	}

	public void setFirstEntered(final String firstEntered) {
		this.firstEntered = firstEntered;
	}

	public PersonComponentAssignmentPK getId() {
		return this.id;
	}
	
	public Long getLongId()
	{
		return Long.valueOf(component + "" + person);
	}

	public void setId(final PersonComponentAssignmentPK id) {
		this.id = id;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public Long getContext() {
		return this.context;
	}

	public void setContext(final Long context) {
		this.context = context;
	}

	public PersonComponentAssignment()
	{

	}

}
