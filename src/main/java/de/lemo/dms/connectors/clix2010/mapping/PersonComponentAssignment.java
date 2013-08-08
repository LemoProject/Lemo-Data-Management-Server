/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/PersonComponentAssignment.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/PersonComponentAssignment.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table PersonComponentAssignment.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "PERSON_COMPONENT_ASSIGNMENT")
public class PersonComponentAssignment implements IClixMappingClass {

	private PersonComponentAssignmentPK id;
	private Long component;
	private Long person;
	private Long context;
	private String firstEntered;

	@Column(name="FIRST_ENTERED")
	public String getFirstEntered() {
		return this.firstEntered;
	}

	public void setFirstEntered(final String firstEntered) {
		this.firstEntered = firstEntered;
	}

	@EmbeddedId
	public PersonComponentAssignmentPK getId() {
		return this.id;
	}
	
	@Transient
	public Long getLongId()
	{
		return Long.valueOf(component + "" + person);
	}

	public void setId(final PersonComponentAssignmentPK id) {
		this.id = id;
	}

	@Column(name="COMPONENT_ID")
	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	@Column(name="PERSON_ID")
	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	@Column(name="CONTEXT")
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
