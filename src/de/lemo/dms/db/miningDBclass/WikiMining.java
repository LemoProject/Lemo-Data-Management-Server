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
	private long timecreated;
	private long timemodified;
	
	private Set<CourseWikiMining> course_wiki = new HashSet<CourseWikiMining>();
	private Set<WikiLogMining> wiki_log = new HashSet<WikiLogMining>();

	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof WikiMining))
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
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribute time created
	 * @param timecreated the time stamp when the wiki was created
	 */
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	
	
	/** standard getter for the attribute time modified
	 * @return the time stamp when the quiz was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribute time modified
	 * @param timemodified the time stamp when the quiz was changed the last time
	 */
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribute course_wiki
	 * @param course_wiki a set of entries in the course_wiki table which relate this wiki to courses
	 */
	public void setCourse_wiki(Set<CourseWikiMining> course_wiki) {
		this.course_wiki = course_wiki;
	}
	/** standard getter for the attribute course_wiki
	 * @return a set of entries in the course_wiki table which relate this wiki to courses
	 */	
	public Set<CourseWikiMining> getCourse_wiki() {
		return course_wiki;
	}
	/** standard add method for the attribute course_wiki
	 * @param course_wiki_add this entry will be added to the list of course_wiki in this wiki
	 * */
	public void addCourse_wiki(CourseWikiMining course_wiki_add){	
		course_wiki.add(course_wiki_add);
	}
	/** standard setter for the attribute wiki_log
	 * @param wiki_log a set of entries in the wiki_log table which are related to this wiki
	 */
	public void setWiki_log(Set<WikiLogMining> wiki_log) {
		this.wiki_log = wiki_log;
	}
	/** standard getter for the attribute wiki_log
	 * @return a set of entries in the wiki_log table which are related to this wiki
	 */	
	public Set<WikiLogMining> getWiki_log() {
		return wiki_log;
	}
	/** standard add method for the attribute wiki_log
	 * @param wiki_log_add this entry will be added to the list of wiki_log in this quiz
	 * */
	public void addWiki_log(WikiLogMining wiki_log_add){	
		wiki_log.add(wiki_log_add);
	}
}
