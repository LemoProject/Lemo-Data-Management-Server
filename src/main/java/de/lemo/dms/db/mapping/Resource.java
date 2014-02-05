/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Resource.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Resource.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

/** 
 * This class represents the table resource. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_resource")
public class Resource implements IMapping{

	private long id;
	private String title;
	private String url;
	private ResourceType type;
	private long platform;
	
	private Set<CourseResource> courseResources = new HashSet<CourseResource>();	
	private Set<EventLog> eventLogs = new HashSet<EventLog>();
	private Set<CollaborativeLog> collaborativeLogs = new HashSet<CollaborativeLog>();
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof Resource)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Resource)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}


	
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Lob
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public ResourceType getType() {
		return type;
	}
	public void setType(ResourceType type) {
		this.type = type;
	}
	
	public void setEventLogs(final Set<EventLog> eventLogs) {
		this.eventLogs = eventLogs;
	}


	@OneToMany(mappedBy="resource")
	public Set<EventLog> getEventLogs() {
		return this.eventLogs;
	}

	public void addEventLog(final EventLog eventLogs) {
		this.eventLogs.add(eventLogs);
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseResources
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseResources(final Set<CourseResource> courseResources) {
		this.courseResources = courseResources;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="resource")
	public Set<CourseResource> getCourseResources() {
		return this.courseResources;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseResource
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseResource(final CourseResource courseResource) {
		this.courseResources.add(courseResource);
	}

	/**
	 * @return the collaborativeLogs
	 */
	@OneToMany(mappedBy="resource")
	public Set<CollaborativeLog> getCollaborativeLogs() {
		return collaborativeLogs;
	}

	/**
	 * @param collaborativeLogs the collaborativeLogs to set
	 */
	public void setCollaborativeLogs(Set<CollaborativeLog> collaborativeLogs) {
		this.collaborativeLogs = collaborativeLogs;
	}
	
	public void addCollaborativeLog(CollaborativeLog collaboritiveLog)
	{
		this.collaborativeLogs.add(collaboritiveLog);
	}

	/**
	 * @return the platform
	 */
	@Column(name="platform")
	public long getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(long platform) {
		this.platform = platform;
	}
	
}
