package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CourseObject {

	private Long id;	
	private String title;
	private String description;
	private Long participants;
	private Long lastRequest;
	
	public CourseObject()
	{}
	
	public CourseObject(Long id, String title, String description, Long participants, Long lastRequest)
	{
		this.id = id;
		this.title = title;
		this.participants = participants;
		this.description = description;
		this.lastRequest = lastRequest;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParticipants(Long participants) {
		this.participants = participants;
	}

	public void setLastRequest(Long lastRequest) {
		this.lastRequest = lastRequest;
	}

	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public Long getParticipants() {
		return participants;
	}
	public Long getLastRequest() {
		return lastRequest;
	}
}
