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
