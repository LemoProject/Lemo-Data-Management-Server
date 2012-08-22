package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


/**This class represents the log table for the wiki object.*/
public class WikiLogMining implements ILogMining, IMappingClass{

	private long id;
	private WikiMining wiki;
	private	UserMining user;
	private CourseMining course;
	private String action;
	private long timestamp;
	private long duration;
	
	@Override
	public int compareTo(ILogMining arg0) {
		ILogMining s;
		try{
			s = arg0;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.timestamp > s.getTimestamp())
			return 1;
		if(this.timestamp < s.getTimestamp())
			return -1;
		return 0;
	}
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof WikiLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof WikiLogMining))
			return true;
		return false;
	}
	
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getTitle()
	{
		return this.wiki.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.wiki == null ? null : this.wiki.getId();
	}

	/** standard getter for the attribute id
	 * @return the identifier of the log entry
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribute action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}
	/** standard setter for the attribute action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	
	/** standard getter for the attribute 
	 * @return the time stamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribute
	 * @param timestamp the time stamp the logged action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/** standard getter for the attribute course
	 * @return the the course in which the action on the wiki occur
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** parameterized setter for the attribute course
	 * @param course the id of the course in which the action on the wiki occur as long value
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of courses in the mining database, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {	
		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addWiki_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addWiki_log(this);
		}
	}
	/** standard setter for the attribute course
	 * @param course the the course in which the action on the wiki occur
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	
	/** standard getter for the attribute user
	 * @return the user who interact with the resource
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribute user
	 * @param user the user who interact with the resource
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribute 
	 * @param user the id of the user who interact with the resource
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the mining database, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addWiki_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addWiki_log(this);
		}
	}
	
	/** standard getter for the attribute wiki
	 * @return the wiki with which was interacted
	 */	
	public WikiMining getWiki() {
		return wiki;
	}
	/** standard setter for the attribute wiki
	 * @param wiki the wiki with which was interacted
	 */	
	public void setWiki(WikiMining wiki) {
		this.wiki = wiki;
	}
	/** parameterized setter for the attribute wiki
	 * @param wiki the id of the wiki with which was interacted
	 * @param wikiMining a list of new added wiki, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining a list of wiki in the mining database, which is searched for the wiki with the id submitted in the wiki parameter
	 */	
	public void setWiki(long wiki, HashMap<Long, WikiMining> wikiMining, HashMap<Long, WikiMining> oldWikiMining) {		
		
		if(wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addWiki_log(this);
		}
		if(this.wiki == null && oldWikiMining.get(wiki) != null)
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addWiki_log(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 1008L;
	}
}
