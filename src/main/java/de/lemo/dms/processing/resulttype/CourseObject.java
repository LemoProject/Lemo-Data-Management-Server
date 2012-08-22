package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CourseObject {

	private Long id;	
	private String title;
	private String description;
	private Long participants;
	private Long lastRequest;
	private Long firstRequest;
	
	public CourseObject()
	{}
	
	public CourseObject(Long id, String title, String description, Long participants, Long lastRequest, Long firstRequest)
	{
		this.id = id;
		this.title = title;
		this.participants = participants;
		this.description = description;
		this.lastRequest = lastRequest;
		this.firstRequest = firstRequest;
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
	
	public void setFirstRequest(Long firstRequest) {
		this.firstRequest = firstRequest;
	}

	@XmlElement
	public Long getId() {
		return id;
	}
	
	@XmlElement
	public String getTitle() {
		return title;
	}
	
	@XmlElement
	public String getDescription() {
		return description;
	}
	
	@XmlElement
	public Long getParticipants() {
		return participants;
	}
	
	@XmlElement
	public Long getLastRequest() {
		return lastRequest;
		
	}
	
	@XmlElement
	public Long getFirstRequest() {
		return firstRequest;
	}
}
