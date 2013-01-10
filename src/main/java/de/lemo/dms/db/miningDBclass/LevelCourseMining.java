package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class LevelCourseMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private	LevelMining level;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof DegreeCourseMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof DegreeCourseMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between department and resource
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between department and resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut 
	 * @return a department in which the resource is used
	 */	
	public CourseMining getCourse() {
		return course;
	}

	public void setCourse(CourseMining course) {
		this.course = course;
	}

	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addLevelCourse(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addLevelCourse(this);
		}
	}
	public LevelMining getLevel() {
		return level;
	}
	public void setLevel(LevelMining degree) {
		this.level = degree;
	}

	public void setLevel(long level, HashMap<Long, LevelMining> levelMining, HashMap<Long, LevelMining> oldLevelMining) {		
		
		if(levelMining.get(level) != null)
		{
			this.level = levelMining.get(level);
			levelMining.get(level).addLevelCourse(this);
		}
		if(this.level == null && oldLevelMining.get(level) != null)
		{
			this.level = oldLevelMining.get(level);
			oldLevelMining.get(level).addLevelCourse(this);
		}
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
}
