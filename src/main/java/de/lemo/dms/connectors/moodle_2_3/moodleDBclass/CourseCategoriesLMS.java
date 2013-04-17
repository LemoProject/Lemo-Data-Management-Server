/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseCategories_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table CourseCategories.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class CourseCategoriesLMS {

	private long id;
	private String title;
	private String path;
	private long depth;
	private long timemodified;

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

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

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public long getDepth() {
		return this.depth;
	}

	public void setDepth(final long depth) {
		this.depth = depth;
	}

}
