/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryStatePK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryStatePK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for ForumEntryState.
 * 
 * @author S.Schwarzrock
 *
 */
public class ForumEntryStatePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7895343428630427499L;
	private Long user;
	private Long entry;

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
		if (a.getUser() != this.user) {
			return false;
		}
		if (a.getEntry() != this.entry) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.entry.hashCode();
		hc = (17 * hc) + this.user.hashCode();
		return hc;
	}

	public long getUser() {
		return this.user;
	}

	public void setUser(final long user) {
		this.user = user;
	}

	public long getEntry() {
		return this.entry;
	}

	public void setEntry(final long entry) {
		this.entry = entry;
	}

	public ForumEntryStatePK()
	{
	}

}
