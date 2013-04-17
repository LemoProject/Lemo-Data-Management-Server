/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Question.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class QuestionLMS {

	private long id;
	private long category;
	private String name;
	private String questiontext;
	private String qtype;
	private long timecreated;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCategory() {
		return this.category;
	}

	public void setCategory(final long category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getQuestiontext() {
		return this.questiontext;
	}

	public void setQuestiontext(final String questiontext) {
		this.questiontext = questiontext;
	}

	public String getQtype() {
		return this.qtype;
	}

	public void setQtype(final String qtype) {
		this.qtype = qtype;
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
}
