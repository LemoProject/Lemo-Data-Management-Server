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
	private Double maxgrade;
	private long timeopen;
	private long timeclose;	
	private long timecreated;
	private long timemodified;
	private Long platform;
	
	private Set<ScormLogMining> scorm_log = new HashSet<ScormLogMining>();
	private Set<CourseScormMining> course_scorm = new HashSet<CourseScormMining>();

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ScormMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ScormMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut timestamp
	 * @return the timestamp the scorm package will be accessable after by students
	 */
	public long getTimeopen() {
		return timeopen;
	}
	/** standard setter for the attribut timestamp
	 * @param timeopen the timestamp the scorm package will be accessable after by students
	 * */		
	public void setTimeopen(long timeopen) {
		this.timeopen = timeopen;
	}
	/** standard getter for the attribut timeclose
	 * @return the timestamp after that the scorm package will be not accessable any more by students
	 */	
	public long getTimeclose() {
		return timeclose;
	}
	/** standard setter for the attribut timeclose
	 * @param timeclose the timestamp after that the scorm package will be not accessable any more by students
	 * */		
	public void setTimeclose(long timeclose) {
		this.timeclose = timeclose;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the scorm package was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the scorm package was created
	 * */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the scorm package was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the scorm package was changed the last time
	 * */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut title
	 * @param title the title of the scorm package
	 * */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut title
	 * @return the title of the scorm package
	 */	
	public String getTitle() {
		return title;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier of the scorm package
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the scorm package
	 */		
	public void setId(long id) {
		this.id = id;
	}
	
	
	/** standard getter for the attribut maxgrade
	 * @return the maximum grade which is set for the scorm package
	 */
	public Double getMaxgrade() {
		return maxgrade;
	}
	/** standard setter for the attribut maxgrade
	 * @param maxgrade the maximum grade which is set for the scorm package
	 * */
	public void setMaxgrade(double maxgrade) {
		this.maxgrade = maxgrade;
	}
	/** standard setter for the attribut type
	 * @param type the type of this scorm package
	 * */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribut type
	 * @return the type of this scorm package
	 * */
	public String getType() {
		return type;
	}
	/** standard setter for the attribut scorm_log
	 * @param scorm_log a set of entrys in the scorm_log table which are related with this scorm package
	 * */	
	public void setScorm_log(Set<ScormLogMining> scorm_log) {
		this.scorm_log = scorm_log;
	}
	/** standard getter for the attribut scorm_log
	 * @return a set of entrys in the quiz_log table which are related with this scorm package
	 */	
	public Set<ScormLogMining> getScorm_log() {
		return scorm_log;
	}
	/** standard setter for the attribut scorm_log
	 * @param scorm_log_add this entry will be added to the list of scorm_log in this scorm package
	 * */	
	public void addScorm_log(ScormLogMining scorm_log_add){	
		scorm_log.add(scorm_log_add);
	}
	/** standard setter for the attribut course_scorm
	 * @param course_scorm a set of entrys in the course_scorm table which are related with this scorm package
	 * */	
	public void setCourse_scorm(Set<CourseScormMining> course_scorm) {
		this.course_scorm = course_scorm;
	}
	/** standard getter for the attribut course_scorm
	 * @return a set of entrys in the course_scorm table which are related with this scorm package
	 */	
	public Set<CourseScormMining> getCourse_scorm() {
		return course_scorm;
	}
	/** standard setter for the attribut course_scorm
	 * @param course_scorm_add this entry will be added to the list of course_scorm in this scorm package
	 * */	
	public void addCourse_scorm(CourseScormMining course_scorm_add){	
		course_scorm.add(course_scorm_add);
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