/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimesPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for ScormSessionTimes.
 * 
 * @author S.Schwarzrock
 *
 */
public class ScormSessionTimesPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333399944075685332L;
	private Long component;
	private Long person;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof ScormSessionTimesPK)) {
			return false;
		}
		final ScormSessionTimesPK a = (ScormSessionTimesPK) arg;
		if (a.getComponent() != this.component) {
			return false;
		}
		if (a.getPerson() != this.person) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.person.hashCode();
		hc = (17 * hc) + this.component.hashCode();
		return hc;
	}

	public ScormSessionTimesPK()
	{

	}

	public ScormSessionTimesPK(final Long component, final long person)
	{
		this.component = component;
		this.person = person;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

}
