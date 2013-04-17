/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/ForumLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table Forum.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ForumLMS {

	private long id;
	private long course;
	// private String type; //enum('single','news','general','social','eachuser','teacher','qanda')
	private String name;
	private String intro;
	private long assesstimestart;
	private long assesstimefinish;
	private long timemodified;

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

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(final String intro) {
		this.intro = intro;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getAssesstimestart() {
		return this.assesstimestart;
	}

	public void setAssesstimestart(final long assesstimestart) {
		this.assesstimestart = assesstimestart;
	}

	public long getAssesstimefinish() {
		return this.assesstimefinish;
	}

	public void setAssesstimefinish(final long assesstimetfinish) {
		this.assesstimefinish = assesstimetfinish;
	}
}
