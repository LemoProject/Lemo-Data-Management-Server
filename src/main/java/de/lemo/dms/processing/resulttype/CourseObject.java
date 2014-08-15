/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/CourseObject.java
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
		this.hash = hash;
		this.genderSupport = genderSupport;
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
