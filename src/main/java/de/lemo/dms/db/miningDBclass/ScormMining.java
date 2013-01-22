package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/**This class represents the table assignment.*/
public class ScormMining implements IMappingClass, ILearningObject, IRatedObject {

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;	
	private long timeCreated;
	private long timeModified;
	private Long platform;
	
	private Set<ScormLogMining> scormLogs = new HashSet<ScormLogMining>();
	private Set<CourseScormMining> courseScorms = new HashSet<CourseScormMining>();

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof ScormMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ScormMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute timestamp
	 * @return the timestamp the scorm package will be accessible after by students
	 */
	public long getTimeOpen() {
		return timeOpen;
	}
	/** standard setter for the attribute timestamp
	 * @param timeOpen the timestamp the scorm package will be accessible after by students
	 * */		
	public void setTimeOpen(long timeOpen) {
		this.timeOpen = timeOpen;
	}
	/** standard getter for the attribute timeclose
	 * @return the timestamp after that the scorm package will be not accessible any more by students
	 */	
	public long getTimeClose() {
		return timeClose;
	}
	/** standard setter for the attribute timeclose
	 * @param timeClose the timestamp after that the scorm package will be not accessible any more by students
	 * */		
	public void setTimeClose(long timeClose) {
		this.timeClose = timeClose;
	}
	/** standard getter for the attribute timeCreated
	 * @return the timestamp when the scorm package was created
	 */	
	public long getTimeCreated() {
		return timeCreated;
	}
	/** standard setter for the attribute timeCreated
	 * @param timeCreated the timestamp when the scorm package was created
	 * */	
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	/** standard getter for the attribute timeModified
	 * @return the timestamp when the scorm package was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	/** standard setter for the attribute timeModified
	 * @param timeModified the timestamp when the scorm package was changed the last time
	 * */	
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	/** standard setter for the attribute title
	 * @param title the title of the scorm package
	 * */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribute title
	 * @return the title of the scorm package
	 */	
	public String getTitle() {
		return title;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the scorm package
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the scorm package
	 */		
	public void setId(long id) {
		this.id = id;
	}
	
	
	/** standard getter for the attribute maxgrade
	 * @return the maximum grade which is set for the scorm package
	 */
	public Double getMaxGrade() {
		return maxGrade;
	}
	/** standard setter for the attribute maxgrade
	 * @param maxGrade the maximum grade which is set for the scorm package
	 * */
	public void setMaxGrade(double maxGrade) {
		this.maxGrade = maxGrade;
	}
	/** standard setter for the attribute type
	 * @param type the type of this scorm package
	 * */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribute type
	 * @return the type of this scorm package
	 * */
	public String getType() {
		return type;
	}
	/** standard setter for the attribute scorm_log
	 * @param scormLogs a set of entries in the scorm_log table which are related with this scorm package
	 * */	
	public void setScormLogs(Set<ScormLogMining> scormLogs) {
		this.scormLogs = scormLogs;
	}
	/** standard getter for the attribute scorm_log
	 * @return a set of entries in the quiz_log table which are related with this scorm package
	 */	
	public Set<ScormLogMining> getScormLogs() {
		return scormLogs;
	}
	/** standard setter for the attribute scorm_log
	 * @param scormLog this entry will be added to the list of scorm_log in this scorm package
	 * */	
	public void addScormLog(ScormLogMining scormLog){	
		scormLogs.add(scormLog);
	}
	/** standard setter for the attribute course_scorm
	 * @param courseScorms a set of entries in the course_scorm table which are related with this scorm package
	 * */	
	public void setCourseScorms(Set<CourseScormMining> courseScorms) {
		this.courseScorms = courseScorms;
	}
	/** standard getter for the attribute course_scorm
	 * @return a set of entries in the course_scorm table which are related with this scorm package
	 */	
	public Set<CourseScormMining> getCourseScorms() {
		return courseScorms;
	}
	/** standard setter for the attribute course_scorm
	 * @param courseScorm this entry will be added to the list of course_scorm in this scorm package
	 * */	
	public void addCourseScorm(CourseScormMining courseScorm){	
		this.courseScorms.add(courseScorm);
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	@Override
	public Long getPrefix() {
		return 17L;
	}
}