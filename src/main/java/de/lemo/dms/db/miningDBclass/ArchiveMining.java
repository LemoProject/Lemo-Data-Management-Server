package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;


public class ArchiveMining {
	
	private Long id;
	private Long platform;
	private String title;
	
	private Set<ArchiveLogMining> assignmentLogs = new HashSet<ArchiveLogMining>();
	private Set<CourseArchiveMining> courseAssignments = new HashSet<CourseArchiveMining>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPlatform() {
		return platform;
	}
	
	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<ArchiveLogMining> getAssignmentLogs() {
		return assignmentLogs;
	}
	
	public void setAssignmentLogs(Set<ArchiveLogMining> assignmentLogs) {
		this.assignmentLogs = assignmentLogs;
	}
	
	public Set<CourseArchiveMining> getCourseAssignments() {
		return courseAssignments;
	}
	
	public void setCourseAssignments(Set<CourseArchiveMining> courseAssignments) {
		this.courseAssignments = courseAssignments;
	}


}
