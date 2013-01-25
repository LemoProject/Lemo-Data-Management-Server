/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/LevelMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

public class LevelMining {

	private long id;
	private String title;
	private Long platform;
	private long depth;

	private Set<LevelAssociationMining> levelAssociations = new HashSet<LevelAssociationMining>();
	private Set<LevelCourseMining> levelCourses = new HashSet<LevelCourseMining>();

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	/**
	 * standard setter for the attribute department_degreee
	 * 
	 * @param department_degree
	 *            a set of entries in the department_degreee table which relate the degrees to the departments
	 */
	public void setLevelAssociations(final Set<LevelAssociationMining> levelAssociation) {
		this.levelAssociations = levelAssociation;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a set of entries in the department_degreee table which relate the degree to the departments
	 */
	public Set<LevelAssociationMining> getLevelAssociations() {
		return this.levelAssociations;
	}

	/**
	 * standard add method for the attribute department_degree
	 * 
	 * @param department_degree_add
	 *            this entry will be added to the list of department_degree in this department
	 */
	public void addLevelAssociation(final LevelAssociationMining LevelAssociationAdd) {
		this.levelAssociations.add(LevelAssociationAdd);
	}

	/**
	 * standard setter for the attribute department_degreee
	 * 
	 * @param degree_course
	 *            a set of entries in the degree_course table which relate the degrees to the courses
	 */
	public void setLevelCourses(final Set<LevelCourseMining> levelCourse) {
		this.levelCourses = levelCourse;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a set of entries in the degree_course table which relate the degree to the courses
	 */
	public Set<LevelCourseMining> getLevelCourses() {
		return this.levelCourses;
	}

	/**
	 * standard add method for the attribute degree_course
	 * 
	 * @param degree_course_add
	 *            this entry will be added to the list of degree_course in this department
	 */
	public void addLevelCourse(final LevelCourseMining levelCourseAdd) {
		this.levelCourses.add(levelCourseAdd);
	}

	public long getDepth() {
		return this.depth;
	}

	public void setDepth(final long depth) {
		this.depth = depth;
	}

}
