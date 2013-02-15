/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TTestSpecificationPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for TTestSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
public class TTestSpecificationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841009607798919961L;
	private Long task;
	private Long test;

	public TTestSpecificationPK()
	{
	}

	public TTestSpecificationPK(final Long task, final Long test)
	{
		this.task = task;
		this.test = test;

	}

	public Long getTask() {
		return this.task;
	}

	public void setTask(final Long task) {
		this.task = task;
	}

	public Long getTest() {
		return this.test;
	}

	public void setTest(final Long test) {
		this.test = test;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TTestSpecificationPK)) {
			return false;
		}
		final TTestSpecificationPK a = (TTestSpecificationPK) arg;
		if (a.getTask() != this.task) {
			return false;
		}
		if (a.getTest() != this.test) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.test.hashCode();
		hc = (17 * hc) + this.test.hashCode();
		return hc;
	}

}
