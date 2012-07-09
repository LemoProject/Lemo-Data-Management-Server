package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TeamExerciseGroupMember {

	private Long id;
	private Long portfolio;
	private Long exerciseGroup;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public long getPortfolio() {
		return portfolio;
	}


	public void setPortfolio(Long portfolio) {
		this.portfolio = portfolio;
	}


	public long getExerciseGroup() {
		return exerciseGroup;
	}


	public void setExerciseGroup(Long exerciseGroup) {
		this.exerciseGroup = exerciseGroup;
	}


	public TeamExerciseGroupMember()
	{}
	
}
