/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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

	public String getString()
	{
		return "ChatProtocol$$$" + this.id + "$$$"
				+ this.getChatSource() + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getChatroom() + "$$$"
				+ this.getPerson();
	}

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
