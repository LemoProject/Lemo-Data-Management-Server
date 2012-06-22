package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TTestSpecification {
	
	private TTestSpecificationPK id;
	private long task;
	private long test;
	
	
	
	public TTestSpecificationPK getId() {
		return id;
	}



	public void setId(TTestSpecificationPK id) {
		this.id = id;
	}



	public long getTask() {
		return task;
	}



	public void setTask(long task) {
		this.task = task;
	}



	public long getTest() {
		return test;
	}



	public void setTest(long test) {
		this.test = test;
	}
	
	public TTestSpecification(){}

}
