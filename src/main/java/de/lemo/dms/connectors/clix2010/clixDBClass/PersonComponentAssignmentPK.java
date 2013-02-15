/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignmentPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for PersonComponentAssignment.
 * 
 * @author S.Schwarzrock
 *
 */
public class PersonComponentAssignmentPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245182973491294243L;
	private Long person;
	private Long component;
	private Long context;

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getContext() {
		return this.context;
	}

	public void setContext(final Long context) {
		this.context = context;
	}

	public PersonComponentAssignmentPK()
	{

	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof PersonComponentAssignmentPK)) {
			return false;
		}
		final PersonComponentAssignmentPK a = (PersonComponentAssignmentPK) arg;
		if (a.getComponent() != this.component) {
			return false;
		}
		if (a.getPerson() != this.person) {
			return false;
		}
		if (a.getContext() != this.context) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.person.hashCode() * 17) + (this.component.hashCode() * 19) + (this.context.hashCode() * 23);
	}

}
