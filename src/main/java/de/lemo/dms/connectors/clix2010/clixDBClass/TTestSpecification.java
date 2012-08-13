package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TTestSpecification  implements IClixMappingClass{
	
	private TTestSpecificationPK id;
	private Long task;
	private Long test;
	
	
	
	public TTestSpecificationPK getId() {
		return id;
	}
	
	public String getString()
	{
		return "TTestSpecificationä$"
				+this.getTask()+"ä$"
				+this.getTest();
	}



	public void setId(TTestSpecificationPK id) {
		this.id = id;
	}



	public Long getTask() {
		return task;
	}



	public void setTask(Long task) {
		this.task = task;
	}



	public Long getTest() {
		return test;
	}



	public void setTest(Long test) {
		this.test = test;
	}
	
	public TTestSpecification(){}

}
