package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CourseObject {

	@XmlElement
	private Long id;	
	@XmlElement
	private String title;
	@XmlElement
	private String description;
	@XmlElement
	private Long participants;
	@XmlElement
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
