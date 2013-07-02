/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimesPK.java
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
