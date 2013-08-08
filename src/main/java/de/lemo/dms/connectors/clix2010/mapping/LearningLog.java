/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/LearningLog.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/BiTrackContentImpressions.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table LearningLog.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "LEARNING_LOG")
public class LearningLog {
	
	private Long id;
	private Long person;
	private Long course;
	private Long component;
	private String lastUpdated;
	private String startDate;
	private String endDate;
	private Double evaluatedScore;
	private Long typeOfModification;
	
	@Id
	@Column(name="LEARNING_LOG_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="PERSON_ID")
	public Long getPerson() {
		return person;
	}
	
	public void setPerson(Long person) {
		this.person = person;
	}
	
	@Column(name="COURSE_ID")
	public Long getCourse() {
		return course;
	}
	
	public void setCourse(Long course) {
		this.course = course;
	}
	
	@Column(name="COMPONENT_ID")
	public Long getComponent() {
		return component;
	}
	
	public void setComponent(Long component) {
		this.component = component;
	}
	
	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@Column(name="START_DATE")
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="END_DATE")
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="EVALUATED_SCORE")
	public Double getEvaluatedScore() {
		return evaluatedScore;
	}
	
	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	@Column(name="TYPE_OF_MODIFICATION")
	public Long getTypeOfModification() {
		return typeOfModification;
	}

	public void setTypeOfModification(Long typeOfModification) {
		this.typeOfModification = typeOfModification;
	}
	
	
	

}
