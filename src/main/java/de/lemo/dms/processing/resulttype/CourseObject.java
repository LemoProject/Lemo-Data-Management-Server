/**
 * File ./main/java/de/lemo/dms/processing/resulttype/CourseObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a course as object
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class CourseObject {

	private Long id;
	private String title;
	private String description;
	private int participants;
	private Long lastRequest;
	private Long firstRequest;
	private Long hash;
	private boolean genderSupport;

	public CourseObject()
	{
	}

	public CourseObject(final Long id, final String title, final String description, final int participants,
			final Long lastRequest, final Long firstRequest, final Long hash, boolean genderSupport)
	{
		this.id = id;
		this.title = title;
		this.participants = participants;
		this.description = description;
		this.lastRequest = lastRequest;
		this.firstRequest = firstRequest;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setParticipants(final int participants) {
		this.participants = participants;
	}

	public void setLastRequest(final Long lastRequest) {
		this.lastRequest = lastRequest;
	}

	public void setFirstRequest(final Long firstRequest) {
		this.firstRequest = firstRequest;
	}

	@XmlElement
	public Long getId() {
		return this.id;
	}

	@XmlElement
	public String getTitle() {
		return this.title;
	}

	@XmlElement
	public String getDescription() {
		return this.description;
	}

	@XmlElement
	public int getParticipants() {
		return this.participants;
	}

	@XmlElement
	public Long getLastRequest() {
		return this.lastRequest;

	}

	@XmlElement
	public Long getFirstRequest() {
		return this.firstRequest;
	}

	public Long getHash() {
		return hash;
	}

	public void setHash(Long hash) {
		this.hash = hash;
	}

	public boolean isGenderSupport() {
		return genderSupport;
	}

	public void setGenderSupport(boolean genderSupport) {
		this.genderSupport = genderSupport;
	}
}
