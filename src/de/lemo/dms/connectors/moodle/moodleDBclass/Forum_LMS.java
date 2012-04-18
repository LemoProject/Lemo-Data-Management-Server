package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Forum_LMS {

	private long id;
	private long course;
//	private String type; //enum('single','news','general','social','eachuser','teacher','qanda')
	private String name;
	private String intro;
	private long assesstimestart;
	private long assesstimefinish;
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
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public long getAssesstimestart() {
		return assesstimestart;
	}
	public void setAssesstimestart(long assesstimestart) {
		this.assesstimestart = assesstimestart;
	}
	public long getAssesstimefinish() {
		return assesstimefinish;
	}
	public void setAssesstimefinish(long assesstimetfinish) {
		this.assesstimefinish = assesstimetfinish;
	}
}
