package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListCourseObject {
	private List<CourseObject> courses;

	public ResultListCourseObject() {}
	
	public ResultListCourseObject(List<CourseObject> courses) {
		this.courses = courses;
	}
	
	public List<CourseObject> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseObject> courses) {
		this.courses = courses;
	}
}
