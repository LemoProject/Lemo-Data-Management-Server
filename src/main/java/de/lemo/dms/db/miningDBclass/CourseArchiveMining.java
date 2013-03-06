/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseArchiveMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.miningDBclass;

/** 
 * This class represents the relationship between the courses and archive. 
 * @author Sebastian Schwarzrock
 */
public class CourseArchiveMining {
	
	private long id;
	private CourseMining course;
	private ArchiveMining archive;
	private long platform;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public CourseMining getCourse() {
		return course;
	}
	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	
	public ArchiveMining getArchive() {
		return archive;
	}
	
	public void setArchive(ArchiveMining archive) {
		this.archive = archive;
	}
	
	public long getPlatform() {
		return platform;
	}
	
	public void setPlatform(long platform) {
		this.platform = platform;
	}
	
	

}
