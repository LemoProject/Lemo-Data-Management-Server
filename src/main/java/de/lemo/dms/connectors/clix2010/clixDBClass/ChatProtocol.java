/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ChatProtocol.
 * 
 * @author S.Schwarzrock
 *
 */
public class ChatProtocol implements IClixMappingClass {

	private Long id;
	private Long chatroom;
	private Long person;
	private String chatSource;
	private String lastUpdated;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getChatroom() {
		return this.chatroom;
	}

	public void setChatroom(final Long chatroom) {
		this.chatroom = chatroom;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public String getChatSource() {
		return this.chatSource;
	}

	public void setChatSource(final String chatSource) {
		this.chatSource = chatSource;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ChatProtocol()
	{
	}

}
