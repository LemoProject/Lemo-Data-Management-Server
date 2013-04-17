/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_items_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table GradeItems.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GradeItemsLMS {

	private long id;
	private long courseid;
	private String itemname;
	private String itemmodule;
	private Long iteminstance;
	private double grademax;
	private long timecreated;
	private long timemodified;

	public Long getIteminstance() {
		return this.iteminstance;
	}

	public void setIteminstance(final Long iteminstance) {
		this.iteminstance = iteminstance;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getItemmodule() {
		return this.itemmodule;
	}

	public void setItemmodule(final String itemmodule) {
		this.itemmodule = itemmodule;
	}

	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getCourseid() {
		return this.courseid;
	}

	public void setCourseid(final long courseid) {
		this.courseid = courseid;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(final String itemname) {
		this.itemname = itemname;
	}

	public void setGrademax(final double grademax) {
		this.grademax = grademax;
	}

	public double getGrademax() {
		return this.grademax;
	}

}
