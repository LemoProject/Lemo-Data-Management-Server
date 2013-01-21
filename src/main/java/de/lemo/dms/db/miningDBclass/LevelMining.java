package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

public class LevelMining {
	
	private long id;
	private String title;
	private Long platform;
	private long depth;
	
	
	private Set<LevelAssociationMining> levelAssociation = new HashSet<LevelAssociationMining>();
	private Set<LevelCourseMining> levelCourse = new HashSet<LevelCourseMining>();
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getPlatform() {
		return platform;
	}
	
	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
	/** standard setter for the attribute department_degreee
	 * @param department_degree a set of entries in the department_degreee table which relate the degrees to the departments
	 */	
	public void setLevelAssociation(Set<LevelAssociationMining> levelAssociation) {
		this.levelAssociation = levelAssociation;
	}
	
	/** standard getter for the attribute 
	 * @return a set of entries in the department_degreee table which relate the degree to the departments
	 */	
	public Set<LevelAssociationMining> getLevelAssociation() {
		return this.levelAssociation;
	}
	
	/** standard add method for the attribute department_degree
	 * @param department_degree_add this entry will be added to the list of department_degree in this department
	 * */
	public void addLevelAssociation(LevelAssociationMining LevelAssociationAdd){	
		levelAssociation.add(LevelAssociationAdd);	
	}
	
	/** standard setter for the attribute department_degreee
	 * @param degree_course a set of entries in the degree_course table which relate the degrees to the courses
	 */	
	public void setLevelCourse(Set<LevelCourseMining> levelCourse) {
		this.levelCourse = levelCourse;
	}
	
	/** standard getter for the attribute 
	 * @return a set of entries in the degree_course table which relate the degree to the courses
	 */	
	public Set<LevelCourseMining> getLevelCourse() {
		return this.levelCourse;
	}
	
	/** standard add method for the attribute degree_course
	 * @param degree_course_add this entry will be added to the list of degree_course in this department
	 * */
	public void addLevelCourse(LevelCourseMining levelCourseAdd){	
		levelCourse.add(levelCourseAdd);	
	}
	
	public long getDepth() {
		return depth;
	}
	
	public void setDepth(long depth) {
		this.depth = depth;
	}

	
	

}
