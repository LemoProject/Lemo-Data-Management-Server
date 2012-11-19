package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TeamExerciseGroup  implements IClixMappingClass{

	private Long id;
	private Long component;
	
	public Long getId() {
		return id;
	}
	
	public String getString()
	{
		return "TeamExerciseGroup$$$"
				+this.id+"$$$"
				+this.getComponent();
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
	
	public TeamExerciseGroup()
	{
		
	}
}
