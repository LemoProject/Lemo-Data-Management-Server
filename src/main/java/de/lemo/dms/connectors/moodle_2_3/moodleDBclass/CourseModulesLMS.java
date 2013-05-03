/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Course_Modules_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

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
	private long availablefrom;
	private long availableuntil;

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

	public long getAvailablefrom() {
		return this.availablefrom;
	}

	public void setAvailablefrom(final long availablefrom) {
		this.availablefrom = availablefrom;
	}

	public long getAvailableuntil() {
		return this.availableuntil;
	}

	public void setAvailableuntil(final long availableuntil) {
		this.availableuntil = availableuntil;
	}

}
