package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TQtiEvalAssessment {

	
	private Long id;
	private Long component;
	private Long candidate;
	private Long assessment;
	private Long evaluatedScore;
	private Long evalCount;
	private String lastInvocation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getComponent() {
		return component;
	}

	public void setComponent(Long component) {
		this.component = component;
	}

	public Long getCandidate() {
		return candidate;
	}

	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}

	public Long getAssessment() {
		return assessment;
	}

	public void setAssessment(Long assessment) {
		this.assessment = assessment;
	}

	public Long getEvaluatedScore() {
		return evaluatedScore;
	}

	public void setEvaluatedScore(Long evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	public Long getEvalCount() {
		return evalCount;
	}

	public void setEvalCount(Long evalCount) {
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
