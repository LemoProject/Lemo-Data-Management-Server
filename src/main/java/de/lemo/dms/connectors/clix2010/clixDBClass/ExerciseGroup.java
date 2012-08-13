package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class ExerciseGroup implements IClixMappingClass{
	
	private long id;
	private long associatedCourse;
	
	public ExerciseGroup()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAssociatedCourse() {
		return associatedCourse;
	}

	public void setAssociatedCourse(long associatedCourse) {
		this.associatedCourse = associatedCourse;
	}
	
	public String getString()
	{
		return "ExerciseGroupä$"
				+this.id+"ä$"
				+this.getAssociatedCourse();
	}

}
