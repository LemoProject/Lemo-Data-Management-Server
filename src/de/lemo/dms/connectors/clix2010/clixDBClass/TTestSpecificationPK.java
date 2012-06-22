package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class TTestSpecificationPK implements Serializable {
	
	private Long task;
	private Long test;
	
	public TTestSpecificationPK()
	{}
	
	public TTestSpecificationPK(long task, long test)
	{
		this.task = task;
		this.test = test;
		
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
