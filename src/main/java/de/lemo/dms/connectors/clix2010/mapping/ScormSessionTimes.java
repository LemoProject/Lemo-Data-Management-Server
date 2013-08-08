/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/ScormSessionTimes.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/ScormSessionTimes.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table ScormSessionTimes.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "SCORM_SESSION_TIMES")
public class ScormSessionTimes implements IClixMappingClass {

	private ScormSessionTimesPK id;

	private Long component;
	private Long person;
	private String score;
	private String lastUpdated;
	private String status;

	@EmbeddedId
	public ScormSessionTimesPK getId() {
		return this.id;
	}

	public void setId(final ScormSessionTimesPK id) {
		this.id = id;
	}

	public ScormSessionTimes()
	{

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

	@Column(name="SCORE")
	public String getScore() {
		return this.score;
	}

	public void setScore(final String score) {
		this.score = score;
	}

	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name="STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
