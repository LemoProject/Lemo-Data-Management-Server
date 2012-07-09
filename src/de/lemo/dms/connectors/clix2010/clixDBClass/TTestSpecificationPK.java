package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class TTestSpecificationPK implements Serializable {
	
	private Long task;
	private Long test;
	
	public TTestSpecificationPK()
	{}
	
	public TTestSpecificationPK(Long task, Long test)
	{
		this.task = task;
		this.test = test;
		
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
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof TTestSpecificationPK))
			return false;
		TTestSpecificationPK a = (TTestSpecificationPK)arg;
		if(a.getTask() != this.task)
			return false;
		if(a.getTest() != this.test)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int hc;
		hc = test.hashCode();
		hc = 17 * hc + test.hashCode();
		return hc;
	}

}
