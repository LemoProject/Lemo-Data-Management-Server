/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/LevelCourseMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

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

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
