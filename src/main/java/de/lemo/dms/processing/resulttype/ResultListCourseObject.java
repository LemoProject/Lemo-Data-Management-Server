/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListCourseObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for CourseObject's which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
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
