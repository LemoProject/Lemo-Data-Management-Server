package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TeamExerciseComposingExt {
	
	private long id;
	private long eComposingId;
	private String submissionDeadline;
	
	public TeamExerciseComposingExt()
	{
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long geteComposingId() {
		return eComposingId;
	}
	public void seteComposingId(long eComposingId) {
		this.eComposingId = eComposingId;
	}
	public String getSubmissionDeadline() {
		return submissionDeadline;
	}
	public void setSubmissionDeadline(String submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}
	
	

}
