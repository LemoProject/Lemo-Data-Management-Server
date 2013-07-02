/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/ArchiveMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/ArchiveMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

/** 
 * This class represents the table archive. 
 * @author Sebastian Schwarzrock
 */
public class ArchiveMining {
	
	private Long id;
	private Long platform;
	private String title;
	
	private Set<ArchiveLogMining> archiveLogs = new HashSet<ArchiveLogMining>();
	private Set<CourseArchiveMining> courseArchives = new HashSet<CourseArchiveMining>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPlatform() {
		return platform;
	}
	
	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<ArchiveLogMining> getArchiveLogs() {
		return archiveLogs;
	}
	
	public void setArchiveLogs(Set<ArchiveLogMining> archiveLogs) {
		this.archiveLogs = archiveLogs;
	}
	
	public Set<CourseArchiveMining> getCourseArchives() {
		return courseArchives;
	}
	
	public void setCourseArchives(Set<CourseArchiveMining> courseArchives) {
		this.courseArchives = courseArchives;
	}
	
	/**
	 * standard setter for the attribute course_archive
	 * 
	 * @param courseAssignment
	 *            this entry will be added to the list of course_archive in this archive
	 */
	public void addCourseArchive(final CourseArchiveMining courseArchive)
	{
		this.courseArchives.add(courseArchive);
	}


}
