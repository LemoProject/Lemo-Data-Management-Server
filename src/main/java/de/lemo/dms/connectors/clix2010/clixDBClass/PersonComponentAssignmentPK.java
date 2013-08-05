/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignmentPK.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignmentPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for PersonComponentAssignment.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class PersonComponentAssignmentPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245182973491294243L;
	private Long person_id;
	private Long component_id;
	private Long context;

	

	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}

	public Long getComponent_id() {
		return component_id;
	}

	public void setComponent_id(Long component_id) {
		this.component_id = component_id;
	}

	public Long getContext() {
		return context;
	}

	public void setContext(Long context) {
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
		if (a.getComponent_id() != this.component_id) {
			return false;
		}
		if (a.getPerson_id() != this.person_id) {
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
		return (this.person_id.hashCode() * 17) + (this.component_id.hashCode() * 19) + (this.context.hashCode() * 23);
	}

}
