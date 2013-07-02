/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecificationPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecificationPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for PlatformGroupSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
public class PlatformGroupSpecificationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700969997735490626L;
	private Long group;
	private Long person;

	public Long getGroup() {
		return this.group;
	}

	public void setGroup(final Long group) {
		this.group = group;
	}

	public long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public PlatformGroupSpecificationPK()
	{

	}

	public PlatformGroupSpecificationPK(final Long person, final Long group)
	{
		this.group = group;
		this.person = person;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof PlatformGroupSpecificationPK)) {
			return false;
		}
		final PlatformGroupSpecificationPK a = (PlatformGroupSpecificationPK) arg;
		if (a.getGroup() != this.group) {
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
		hc = this.group.hashCode();
		hc = (17 * hc) + this.person.hashCode();
		return hc;
	}

}
