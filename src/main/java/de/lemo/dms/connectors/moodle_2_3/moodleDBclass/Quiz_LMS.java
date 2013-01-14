package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Quiz_LMS {

	private long id;
	private long course;
	private String name;
	private String intro;
	private String questions;
	private long timeopen;
	private long timeclose;
	private long sumgrade;
	private long grade;
	private long timecreated;
	private long timemodified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCourse() {
		return course;
	}
	public void setCourse(long course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public long getTimeopen() {
		return timeopen;
	}
	public void setTimeopen(long timeopen) {
		this.timeopen = timeopen;
	}
	public long getTimeclose() {
		return timeclose;
	}
	public void setTimeclose(long timeclose) {
		this.timeclose = timeclose;
	}
	public long getSumgrade() {
		return sumgrade;
	}
	public void setSumgrade(long sumgrade) {
		this.sumgrade = sumgrade;
	}
	public long getGrade() {
		return grade;
	}
	public void setGrade(long grade) {
		this.grade = grade;
	}
	public long getTimecreated() {
		return timecreated;
	}
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	
}
