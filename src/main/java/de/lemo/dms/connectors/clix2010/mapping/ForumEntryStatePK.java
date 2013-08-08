/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/ForumEntryStatePK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/ForumEntryStatePK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for ForumEntryState.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class ForumEntryStatePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7895343428630427499L;
	private Long user_id;
	private Long entry_id;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof ForumEntryStatePK)) {
			return false;
		}
		final ForumEntryStatePK a = (ForumEntryStatePK) arg;
		if (a.getUser_id() != this.user_id) {
			return false;
		}
		if (a.getEntry_id() != this.entry_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.entry_id.hashCode();
		hc = (17 * hc) + this.user_id.hashCode();
		return hc;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(Long entry_id) {
		this.entry_id = entry_id;
	}

	public ForumEntryStatePK()
	{
	}

}
