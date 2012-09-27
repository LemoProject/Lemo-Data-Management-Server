package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
/**This class represents the relationship between courses and wikis.*/
public class CourseWikiMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private	WikiMining wiki;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof CourseWikiMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseWikiMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and wiki
	 */
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and wiki
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut course
	 * @return a course in which the wiki is used in
	 */
	public CourseMining getCourse() {
		return course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of a course in which the wiki is used in
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of courses in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_wiki(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_wiki(this);
		}
	}
	/** standard setter for the attribut course
	 * @param course a course in which the wiki is used in
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** standard getter for the attribut wiki
	 * @return the wiki which is used in the course in this entry
	 */
	public WikiMining getWiki() {
		return wiki;
	}
	/** standard setter for the attribut wiki
	 * @param wiki the wiki which is used in the course in this entry
	 */	
	public void setWiki(WikiMining wiki) {
		this.wiki = wiki;
	}
	/** parameterized setter for the attribut wiki
	 * @param wiki the id of the wiki which is used in the course in this entry
	 * @param wikiMining a list of new added wikis, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining a list of wikis in the miningdatabase, which is searched for the wiki with the id submitted in the wiki parameter
	 */	
	public void setWiki(long wiki, HashMap<Long, WikiMining> wikiMining, HashMap<Long, WikiMining> oldWikiMining) {		
       
		if(wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addCourse_wiki(this);
		}
		if(this.wiki == null && oldWikiMining.get(wiki) != null)
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addCourse_wiki(this);
		}
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
