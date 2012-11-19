package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TeamExerciseComposingExt  implements IClixMappingClass{
	
	private Long id;
	private Long eComposingId;
	private String submissionDeadline;
	
	public TeamExerciseComposingExt()
	{
		
	}
	
	public String getString()
	{
		return "TeamExerciseComposingExt$$$"
				+this.id+"$$$"
				+this.getSubmissionDeadline()+"$$$"
				+this.geteComposingId();
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
