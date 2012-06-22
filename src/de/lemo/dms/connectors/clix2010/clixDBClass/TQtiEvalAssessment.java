package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TQtiEvalAssessment {

	
	private long id;
	private long component;
	private long candidate;
	private long assessment;
	private long evaluatedScore;
	private long evalCount;
	private String lastInvocation;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getComponent() {
		return component;
	}

	public void setComponent(long component) {
		this.component = component;
	}

	public long getCandidate() {
		return candidate;
	}

	public void setCandidate(long candidate) {
		this.candidate = candidate;
	}

	public long getAssessment() {
		return assessment;
	}

	public void setAssessment(long assessment) {
		this.assessment = assessment;
	}

	public long getEvaluatedScore() {
		return evaluatedScore;
	}

	public void setEvaluatedScore(long evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	public long getEvalCount() {
		return evalCount;
	}

	public void setEvalCount(long evalCount) {
		this.evalCount = evalCount;
	}

	public String getLastInvocation() {
		return lastInvocation;
	}

	public void setLastInvocation(String lastInvocation) {
		this.lastInvocation = lastInvocation;
	}

	public TQtiEvalAssessment()
	{
		
	}
}
