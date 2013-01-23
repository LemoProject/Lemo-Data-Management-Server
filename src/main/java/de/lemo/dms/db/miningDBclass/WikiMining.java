package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table wiki.*/
public class WikiMining  implements IMappingClass, ILearningObject{
	
	private long id;
	private String title;
	private String summary;
	private long timeCreated;
	private long timeModified;
	private Long platform;
	
	private Set<CourseWikiMining> courseWikis = new HashSet<CourseWikiMining>();
	private Set<WikiLogMining> wikiLogs = new HashSet<WikiLogMining>();

	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof WikiMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof WikiMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the wiki
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the wiki
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribute title
	 * @return the title of the wiki
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribute title
	 * @param title the title of the wiki
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** standard getter for the attribute summary
	 * @return a short text which describes the subject of the wiki
	 */	
	public String getSummary() {
		return summary;
	}
	/** standard setter for the attribute summary
	 * @param summary a short text which describes the subject of the wiki
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/** standard getter for the attribute time created
	 * @return the time stamp when the wiki was created
	 */	
	public long getTimeCreated() {
		return timeCreated;
	}
	/** standard setter for the attribute time created
	 * @param timeCreated the time stamp when the wiki was created
	 */
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	
	/** standard getter for the attribute time modified
	 * @return the time stamp when the quiz was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	/** standard setter for the attribute time modified
	 * @param timeModified the time stamp when the quiz was changed the last time
	 */
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	/** standard setter for the attribute course_wiki
	 * @param courseWikis a set of entries in the course_wiki table which relate this wiki to courses
	 */
	public void setCourseWikis(Set<CourseWikiMining> courseWikis) {
		this.courseWikis = courseWikis;
	}
	/** standard getter for the attribute course_wiki
	 * @return a set of entries in the course_wiki table which relate this wiki to courses
	 */	
	public Set<CourseWikiMining> getCourseWikis() {
		return courseWikis;
	}
	/** standard add method for the attribute course_wiki
	 * @param courseWiki this entry will be added to the list of course_wiki in this wiki
	 * */
	public void addCourseWiki(CourseWikiMining courseWiki){	
		courseWikis.add(courseWiki);
	}
	/** standard setter for the attribute wiki_log
	 * @param wikiLogs a set of entries in the wiki_log table which are related to this wiki
	 */
	public void setWikiLogs(Set<WikiLogMining> wikiLogs) {
		this.wikiLogs = wikiLogs;
	}
	/** standard getter for the attribute wiki_log
	 * @return a set of entries in the wiki_log table which are related to this wiki
	 */	
	public Set<WikiLogMining> getWikiLogs() {
		return wikiLogs;
	}
	/** standard add method for the attribute wiki_log
	 * @param wikiLog this entry will be added to the list of wiki_log in this quiz
	 * */
	public void addWikiLog(WikiLogMining wikiLog){	
		wikiLogs.add(wikiLog);
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
