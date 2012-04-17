package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

/**This class represents the table wiki.*/
public class WikiMining {
	
	private long id;
	private String title;
	private String summary;
	private long timecreated;
	private long timemodified;
	
	private Set<CourseWikiMining> course_wiki = new HashSet<CourseWikiMining>();
	private Set<WikiLogMining> wiki_log = new HashSet<WikiLogMining>();

	/** standard getter for the attribut id
	 * @return the identifier of the wiki
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the wiki
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut title
	 * @return the title of the wiki
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut title
	 * @param title the title of the wiki
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** standard getter for the attribut summary
	 * @return a short text which discribes the subject of the wiki
	 */	
	public String getSummary() {
		return summary;
	}
	/** standard setter for the attribut summary
	 * @param summary a short text which discribes the subject of the wiki
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the wiki was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the wiki was created
	 */
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	
	
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the quiz was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the quiz was changed the last time
	 */
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut course_wiki
	 * @param course_wiki a set of entrys in the course_wiki table which relate this wiki to courses
	 */
	public void setCourse_wiki(Set<CourseWikiMining> course_wiki) {
		this.course_wiki = course_wiki;
	}
	/** standard getter for the attribut course_wiki
	 * @return a set of entrys in the course_wiki table which relate this wiki to courses
	 */	
	public Set<CourseWikiMining> getCourse_wiki() {
		return course_wiki;
	}
	/** standard add method for the attribut course_wiki
	 * @param course_wiki_add this entry will be added to the list of course_wiki in this wiki
	 * */
	public void addCourse_wiki(CourseWikiMining course_wiki_add){	
		course_wiki.add(course_wiki_add);
	}
	/** standard setter for the attribut wiki_log
	 * @param wiki_log a set of entrys in the wiki_log table which are related to this wiki
	 */
	public void setWiki_log(Set<WikiLogMining> wiki_log) {
		this.wiki_log = wiki_log;
	}
	/** standard getter for the attribut wiki_log
	 * @return a set of entrys in the wiki_log table which are related to this wiki
	 */	
	public Set<WikiLogMining> getWiki_log() {
		return wiki_log;
	}
	/** standard add method for the attribut wiki_log
	 * @param wiki_log_add this entry will be added to the list of wiki_log in this quiz
	 * */
	public void addWiki_log(WikiLogMining wiki_log_add){	
		wiki_log.add(wiki_log_add);
	}
}
