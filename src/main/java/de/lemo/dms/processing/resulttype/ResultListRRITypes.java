/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListRRITypes.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for RRITypes objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListRRITypes {

	private List<ResourceRequestInfo> assignments;
	private List<ResourceRequestInfo> courses;
	private List<ResourceRequestInfo> forums;
	private List<ResourceRequestInfo> questions;
	private List<ResourceRequestInfo> quiz;
	private List<ResourceRequestInfo> resources;
	private List<ResourceRequestInfo> scorms;
	private List<ResourceRequestInfo> wikis;

	public void setAssignmentRRI(final List<ResourceRequestInfo> assignments) {
		this.assignments = assignments;
	}

	public void setCourseRRI(final List<ResourceRequestInfo> courses) {
		this.courses = courses;
	}

	public void setForumRRI(final List<ResourceRequestInfo> forums) {
		this.forums = forums;
	}

	public void setQuestionRRI(final List<ResourceRequestInfo> questions) {
		this.questions = questions;
	}

	public void setQuizRRI(final List<ResourceRequestInfo> quiz) {
		this.quiz = quiz;
	}

	public void setResourceRRI(final List<ResourceRequestInfo> resources) {
		this.resources = resources;
	}

	public void setScormRRI(final List<ResourceRequestInfo> scorms) {
		this.scorms = scorms;
	}

	public void setWikiRRI(final List<ResourceRequestInfo> wikis) {
		this.wikis = wikis;
	}

	public ResultListRRITypes()
	{
		this.assignments = new ArrayList<ResourceRequestInfo>();
		this.courses = new ArrayList<ResourceRequestInfo>();
		this.forums = new ArrayList<ResourceRequestInfo>();
		this.questions = new ArrayList<ResourceRequestInfo>();
		this.quiz = new ArrayList<ResourceRequestInfo>();
		this.resources = new ArrayList<ResourceRequestInfo>();
		this.scorms = new ArrayList<ResourceRequestInfo>();
		this.wikis = new ArrayList<ResourceRequestInfo>();
	}

	@XmlElement
	public List<ResourceRequestInfo> getAssignmentRRI()
	{
		return this.assignments;
	}

	@XmlElement
	public List<ResourceRequestInfo> getForumRRI()
	{
		return this.forums;
	}

	@XmlElement
	public List<ResourceRequestInfo> getCoursesRRI()
	{
		return this.courses;
	}

	@XmlElement
	public List<ResourceRequestInfo> getQuestionsRRI()
	{
		return this.questions;
	}

	@XmlElement
	public List<ResourceRequestInfo> getQuizRRI()
	{
		return this.quiz;
	}

	@XmlElement
	public List<ResourceRequestInfo> getResourcesRRI()
	{
		return this.resources;
	}

	@XmlElement
	public List<ResourceRequestInfo> getScormRRI()
	{
		return this.scorms;
	}

	@XmlElement
	public List<ResourceRequestInfo> getWikiRRI()
	{
		return this.wikis;
	}

}
