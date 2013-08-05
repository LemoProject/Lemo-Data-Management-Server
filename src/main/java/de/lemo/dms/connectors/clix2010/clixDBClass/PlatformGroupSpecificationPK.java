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

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for PlatformGroupSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class PlatformGroupSpecificationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700969997735490626L;
	private Long group_id;
	private Long person_id;


	
	
	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}

	public PlatformGroupSpecificationPK()
	{

	}

	public PlatformGroupSpecificationPK(final Long person, final Long group)
	{
		this.group_id = group;
		this.person_id = person;
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
		if (a.getGroup_id() != this.group_id) {
			return false;
		}
		if (a.getPerson_id() != this.person_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.group_id.hashCode();
		hc = (17 * hc) + this.person_id.hashCode();
		return hc;
	}

}
