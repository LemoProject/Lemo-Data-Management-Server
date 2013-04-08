/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.clix2010.clixDBClass;

/**
 * Mapping class for table LearningLog.
 * 
 * @author S.Schwarzrock
 *
 */
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPerson() {
		return person;
	}
	
	public void setPerson(Long person) {
		this.person = person;
	}
	
	public Long getCourse() {
		return course;
	}
	
	public void setCourse(Long course) {
		this.course = course;
	}
	
	public Long getComponent() {
		return component;
	}
	
	public void setComponent(Long component) {
		this.component = component;
	}
	
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Double getEvaluatedScore() {
		return evaluatedScore;
	}
	
	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	public Long getTypeOfModification() {
		return typeOfModification;
	}

	public void setTypeOfModification(Long typeOfModification) {
		this.typeOfModification = typeOfModification;
	}
	
	
	

}
