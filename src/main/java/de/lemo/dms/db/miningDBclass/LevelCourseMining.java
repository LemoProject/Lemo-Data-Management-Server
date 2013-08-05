/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/LevelCourseMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/LevelCourseMining.java
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
 * Represanting an object of the level of an hierarchy
 * @author Sebastian Schwarzrock
 *
 */
@Entity
@Table(name = "level_course")
public class LevelCourseMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private LevelMining level;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof LevelCourseMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof LevelCourseMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between department and resource
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between department and resource
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a department in which the resource is used
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addLevelCourse(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addLevelCourse(this);
		}
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="level_id")
	public LevelMining getLevel() {
		return this.level;
	}

	public void setLevel(final LevelMining degree) {
		this.level = degree;
	}

	public void setLevel(final long level, final Map<Long, LevelMining> levelMining,
			final Map<Long, LevelMining> oldLevelMining) {

		if (levelMining.get(level) != null)
		{
			this.level = levelMining.get(level);
			levelMining.get(level).addLevelCourse(this);
		}
		if ((this.level == null) && (oldLevelMining.get(level) != null))
		{
			this.level = oldLevelMining.get(level);
			oldLevelMining.get(level).addLevelCourse(this);
		}
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
