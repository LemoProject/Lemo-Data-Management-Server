package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
	
	public void setAssignmentRRI(List<ResourceRequestInfo> assignments) {
		this.assignments = assignments;
	}

	public void setCourseRRI(List<ResourceRequestInfo> courses) {
		this.courses = courses;
	}

	public void setForumRRI(List<ResourceRequestInfo> forums) {
		this.forums = forums;
	}

	public void setQuestionRRI(List<ResourceRequestInfo> questions) {
		this.questions = questions;
	}

	public void setQuizRRI(List<ResourceRequestInfo> quiz) {
		this.quiz = quiz;
	}

	public void setResourceRRI(List<ResourceRequestInfo> resources) {
		this.resources = resources;
	}

	public void setScormRRI(List<ResourceRequestInfo> scorms) {
		this.scorms = scorms;
	}

	public void setWikiRRI(List<ResourceRequestInfo> wikis) {
		this.wikis = wikis;
	}

	public ResultListRRITypes()
	{
		assignments = new ArrayList<ResourceRequestInfo>();
		courses = new ArrayList<ResourceRequestInfo>();
		forums = new ArrayList<ResourceRequestInfo>();
		questions = new ArrayList<ResourceRequestInfo>();
		quiz = new ArrayList<ResourceRequestInfo>();
		resources = new ArrayList<ResourceRequestInfo>();
		scorms = new ArrayList<ResourceRequestInfo>();
		wikis = new ArrayList<ResourceRequestInfo>();		
	}
	
	public List<ResourceRequestInfo> getAssignmentRRI()
	{
		return assignments;
	}
	
	public List<ResourceRequestInfo> getForumRRI()
	{
		return forums;
	}
	
	public List<ResourceRequestInfo> getCoursesRRI()
	{
		return courses;
	}
	
	public List<ResourceRequestInfo> getQuestionsRRI()
	{
		return questions;
	}
	
	public List<ResourceRequestInfo> getQuizRRI()
	{
		return quiz;
	}
	
	public List<ResourceRequestInfo> getResourcesRRI()
	{
		return resources;
	}
	
	public List<ResourceRequestInfo> getScormRRI()
	{
		return scorms;
	}
	
	public List<ResourceRequestInfo> getWikiRRI()
	{
		return wikis;
	}
	

}
