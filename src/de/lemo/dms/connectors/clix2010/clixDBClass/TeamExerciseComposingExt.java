package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TeamExerciseComposingExt {
	
	private Long id;
	private Long eComposingId;
	private String submissionDeadline;
	
	public TeamExerciseComposingExt()
	{
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long geteComposingId() {
		return eComposingId;
	}
	
	public void seteComposingId(Long eComposingId) {
		this.eComposingId = eComposingId;
	}
	
	public String getSubmissionDeadline() {
		return submissionDeadline;
	}
	
	public void setSubmissionDeadline(String submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}
	
	

}
