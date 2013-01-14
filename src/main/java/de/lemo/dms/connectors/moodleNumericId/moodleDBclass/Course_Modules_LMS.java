package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class Course_Modules_LMS {
	
	private long id;
	private long course;
	private long module;
	private long instance;
	
	
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
	public long getModule() {
		return module;
	}
	public void setModule(long module) {
		this.module = module;
	}
	public long getInstance() {
		return instance;
	}
	public void setInstance(long instance) {
		this.instance = instance;
	}	
	

}
