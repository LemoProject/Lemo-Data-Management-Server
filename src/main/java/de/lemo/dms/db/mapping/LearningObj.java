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
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;

/** 
 * This class represents the table resource. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_learning_obj")
public class LearningObj implements IMapping, ILearningObject{

	private long id;
	private String title;
	private String interactionType;
	private LearningType type;
	private LearningObj parent;
	
	private Set<CourseLearning> courseLearnings = new HashSet<CourseLearning>();	
	private Set<UserAssessment> userAssessments = new HashSet<UserAssessment>();	
	private Set<AccessLog> accessLogs = new HashSet<AccessLog>();
	private Set<AssessmentLog> assessmentLogs = new HashSet<AssessmentLog>();
	private Set<CollaborationLog> collaborationLogs = new HashSet<CollaborationLog>();
	private Set<LearningAttribute> learningAttributes = new HashSet<LearningAttribute>();
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof LearningObj)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof LearningObj)) {
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public LearningType getType() {
		return type;
	}
	public void setType(LearningType type) {
		this.type = type;
	}
	
	public void setAccessLogs(final Set<AccessLog> accessLogs) {
		this.accessLogs = accessLogs;
	}


	@OneToMany(mappedBy="learning")
	public Set<AccessLog> getAccessLogs() {
		return this.accessLogs;
	}

	public void addAccessLog(final AccessLog accessLog) {
		this.accessLogs.add(accessLog);
	}
	
	public void addAssessmentLog(final AssessmentLog assessmentLog) {
		this.assessmentLogs.add(assessmentLog);
	}
	
	public void addCollaborationLog(final CollaborationLog collaborationLog) {
		this.collaborationLogs.add(collaborationLog);
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseLearningObjects
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseLearningObjects(final Set<CourseLearning> courseLearningObjects) {
		this.courseLearnings = courseLearningObjects;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="learning")
	public Set<CourseLearning> getCourseLearningObjects() {
		return this.courseLearnings;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseLearningObject
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseLearningObject(final CourseLearning courseLearningObject) {
		this.courseLearnings.add(courseLearningObject);
	}
	
	public void addUserAssessment(UserAssessment userAssessment)
	{
		this.userAssessments.add(userAssessment);
	}


	public void setType(final String title, final Map<String, LearningType> learningObjectTypes,
			final Map<String, LearningType> oldLearningObjectTypes) {

		if (learningObjectTypes.get(title) != null)
		{
			this.type = learningObjectTypes.get(title);
			learningObjectTypes.get(title).addLearningObject(this);
		}
		if ((this.type == null) && (oldLearningObjectTypes.get(title) != null))
		{
			this.type = oldLearningObjectTypes.get(title);
			oldLearningObjectTypes.get(title).addLearningObject(this);
		}
	}
	
	public void setParent(final long id, final Map<Long, LearningObj> learningObjs,
			final Map<Long, LearningObj> oldLearningObjs) {

		if (learningObjs.get(id) != null)
		{
			this.parent = learningObjs.get(id);
		}
		if ((this.parent == null) && (oldLearningObjs.get(id) != null))
		{
			this.parent = oldLearningObjs.get(id);
		}
	}

	@Override
	@Transient
	public String getLOType() {
		return this.getType().getType();
	}
	
	/**
	 * @return the parent
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public LearningObj getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(LearningObj parent) {
		this.parent = parent;
	}

	/**
	 * @return the interactionType
	 */
	@Column(name="interaction_type")
	public String getInteractionType() {
		return interactionType;
	}

	/**
	 * @param interactionType the interactionType to set
	 */
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	/**
	 * @return the learningAttributes
	 */
	@OneToMany(mappedBy="learning")
	public Set<LearningAttribute> getLearningAttributes() {
		return learningAttributes;
	}

	/**
	 * @param learningAttributes the learningAttributes to set
	 */
	public void setLearningAttributes(Set<LearningAttribute> learningAttributes) {
		this.learningAttributes = learningAttributes;
	}

	@Override
	public int compareTo(ILearningObject arg0) {
		ILearningObject s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.getId() > s.getId()) {
				return 1;
			}
			if (this.getId() < s.getId()) {
				return -1;
			}
		}
		return 0;
	}
}
