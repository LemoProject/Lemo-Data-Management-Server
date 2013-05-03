/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_Modules_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table CourseModules.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class CourseModulesLMS {

	private long id;
	private long course;
	private long module;
	private long instance;

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

	public long getModule() {
		return this.module;
	}

	public void setModule(final long module) {
		this.module = module;
	}

	public long getInstance() {
		return this.instance;
	}

	public void setInstance(final long instance) {
		this.instance = instance;
	}

}
