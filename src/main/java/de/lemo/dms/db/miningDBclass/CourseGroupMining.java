/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/CourseGroupMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseGroupMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * This class represents the relationship between the courses and groups.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_group")
public class CourseGroupMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private GroupMining group;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseGroupMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseGroupMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and group
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and group
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the group is used
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the group is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the group is used
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {
		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseGroup(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseGroup(this);
		}
	}

	/**
	 * parameterized setter for the attribute group
	 * 
	 * @param group
	 *            a group which is used in the course
	 * @param groupMining
	 *            a list of new added groups, which is searched for the group with the id submitted in the group
	 *            parameter
	 * @param oldGroupMining
	 *            a list of groups in the miningdatabase, which is searched for the group with the id submitted in the
	 *            group parameter
	 */
	public void setGroup(final long group, final Map<Long, GroupMining> groupMining,
			final Map<Long, GroupMining> oldGroupMining) {
		if (groupMining.get(group) != null)
		{
			this.group = groupMining.get(group);
			groupMining.get(group).addCourseGroup(this);
		}
		if ((this.group == null) && (oldGroupMining.get(group) != null))
		{
			this.group = oldGroupMining.get(group);
			oldGroupMining.get(group).addCourseGroup(this);
		}
	}

	/**
	 * standard setter for the attribute group
	 * 
	 * @param group
	 *            a group which is used in the course
	 */
	public void setGroup(final GroupMining group) {
		this.group = group;
	}

	/**
	 * standard getter for the attribute group
	 * 
	 * @return a group which is used in the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_id")
	public GroupMining getGroup() {
		return this.group;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
