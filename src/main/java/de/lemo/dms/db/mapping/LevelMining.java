/**
 * File ./src/main/java/de/lemo/dms/db/mapping/LevelMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/LevelMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * Bean class for the levels of hierarchy on the platform
 */
@Entity
@Table(name = "level")
public class LevelMining {

	private long id;
	private String title;
	private Long platform;
	private long depth;


	private Set<LevelAssociationMining> levelUpperAssociations = new HashSet<LevelAssociationMining>();
	private Set<LevelAssociationMining> levelLowerAssociations = new HashSet<LevelAssociationMining>();
	private Set<LevelCourseMining> levelCourses = new HashSet<LevelCourseMining>();

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Lob
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="platform")
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
	public void setLevelUpperAssociations(final Set<LevelAssociationMining> levelAssociation) {
		this.levelUpperAssociations = levelAssociation;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a set of entries in the department_degreee table which relate the degree to the departments
	 */
	@OneToMany(mappedBy="upper")
	public Set<LevelAssociationMining> getLevelUpperAssociations() {
		return this.levelUpperAssociations;
	}
	
	/**
	 * standard setter for the attribute department_degreee
	 * 
	 * @param department_degree
	 *            a set of entries in the department_degreee table which relate the degrees to the departments
	 */
	public void setLevelLowerAssociations(final Set<LevelAssociationMining> levelLowerAssociation) {
		this.levelLowerAssociations = levelLowerAssociation;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a set of entries in the department_degreee table which relate the degree to the departments
	 */
	@OneToMany(mappedBy="lower")
	public Set<LevelAssociationMining> getLevelLowerAssociations() {
		return this.levelLowerAssociations;
	}

	/**
	 * standard add method for the attribute department_degree
	 * 
	 * @param department_degree_add
	 *            this entry will be added to the list of department_degree in this department
	 */
	public void addLevelAssociation(final LevelAssociationMining levelAssociationAdd) {
		this.levelUpperAssociations.add(levelAssociationAdd);
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
	
	@OneToMany(mappedBy="level")
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

	@Column(name="depth")
	public long getDepth() {
		return this.depth;
	}

	public void setDepth(final long depth) {
		this.depth = depth;
	}

}
