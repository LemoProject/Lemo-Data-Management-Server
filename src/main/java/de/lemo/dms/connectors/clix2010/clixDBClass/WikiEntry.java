/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/WikiEntry.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class WikiEntry implements IClixMappingClass {

	private Long id;
	private Long component;
	private Long creator;
	private Long lastProcessor;
	private Long publisher;
	private String lastUpdated;
	private String publishingDate;
	private String created;

	public String getCreated() {
		return this.created;
	}

	public String getString()
	{
		return "WikiEntry$$$"
				+ this.id + "$$$"
				+ this.getCreated() + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getPublishingDate() + "$$$"
				+ this.getComponent() + "$$$"
				+ this.getCreator() + "$$$"
				+ this.getLastProcessor() + "$$$"
				+ this.getPublisher();
	}

	public void setCreated(final String created) {
		this.created = created;
	}

	public WikiEntry()
	{

	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(final Long creator) {
		this.creator = creator;
	}

	public Long getLastProcessor() {
		return this.lastProcessor;
	}

	public void setLastProcessor(final Long lastProcessor) {
		this.lastProcessor = lastProcessor;
	}

	public Long getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final Long publisher) {
		this.publisher = publisher;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPublishingDate() {
		return this.publishingDate;
	}

	public void setPublishingDate(final String publishingDate) {
		this.publishingDate = publishingDate;
	}

}
