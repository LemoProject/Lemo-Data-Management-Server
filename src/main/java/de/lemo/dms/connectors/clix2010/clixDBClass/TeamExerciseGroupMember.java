package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TeamExerciseGroupMember  implements IClixMappingClass{

	private Long id;
	private Long portfolio;
	private Long exerciseGroup;
	
	
	public Long getId() {
		return id;
	}
	
	public String getString()
	{
		return "TeamExerciseGroupMember$$$"
				+this.id+"$$$"
				+this.getExerciseGroup()+"$$$"
				+this.getPortfolio();
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
