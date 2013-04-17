/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/CourseDisplayLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table CourseDisplay.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class CourseDisplayLMS {

	private long id;
	private long course;
	private String userid;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}
}
