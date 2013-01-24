/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListCourseObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListCourseObject {

	private List<CourseObject> courses;

	public ResultListCourseObject() {
	}

	public ResultListCourseObject(final List<CourseObject> courses) {
		this.courses = courses;
	}

	public List<CourseObject> getCourses() {
		return this.courses;
	}

	public void setCourses(final List<CourseObject> courses) {
		this.courses = courses;
	}
}
