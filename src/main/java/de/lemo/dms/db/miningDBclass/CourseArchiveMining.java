/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseArchiveMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.miningDBclass;

import java.util.Map;

/** 
 * This class represents the relationship between the courses and archive. 
 * @author Sebastian Schwarzrock
 */
public class CourseArchiveMining {
	
	private long id;
	private CourseMining course;
	private ArchiveMining archive;
	private long platform;
	
	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the archive is used
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
			courseMining.get(course).addCourseArchive(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseArchive(this);
		}
	}
	
	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param chat
	 *            the id of a course in which the forum is used
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setArchive(final long archive, final Map<Long, ArchiveMining> archiveMining,
			final Map<Long, ArchiveMining> oldArchiveMining) {
		if (archiveMining.get(archive) != null)
		{
			this.archive = archiveMining.get(archive);
			archiveMining.get(archive).addCourseArchive(this);
		}
		if ((this.archive == null) && (oldArchiveMining.get(archive) != null))
		{
			this.archive = oldArchiveMining.get(archive);
			oldArchiveMining.get(archive).addCourseArchive(this);
		}
	}
	
	
	
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
