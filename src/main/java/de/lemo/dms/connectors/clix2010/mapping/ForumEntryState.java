/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/ForumEntryState.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/ForumEntryState.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table ForumEntryState.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "FORUM_ENTRY_STATE")
public class ForumEntryState implements IClixMappingClass {

	private ForumEntryStatePK id;

	private Long user;
	private Long forum;
	private String lastUpdated;
	private Long entry;

	@EmbeddedId
	public ForumEntryStatePK getId() {
		return this.id;
	}


	public void setId(final ForumEntryStatePK id) {
		this.id = id;
	}

	@Column(name="USER_ID")
	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	@Column(name="FORUM_ID")
	public Long getForum() {
		return this.forum;
	}

	public void setForum(final Long forum) {
		this.forum = forum;
	}

	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name="ENTRY_ID")
	public Long getEntry() {
		return this.entry;
	}

	public void setEntry(final Long entry) {
		this.entry = entry;
	}

	public ForumEntryState()
	{
	}

}
