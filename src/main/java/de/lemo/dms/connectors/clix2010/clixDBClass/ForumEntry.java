/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntry.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ForumEntry.
 * 
 * @author S.Schwarzrock
 *
 */
public class ForumEntry implements IClixMappingClass {

	private Long id;
	private Long forum;
	private Long lastUpdater;
	private String lastUpdated;
	private String title;
	private String content;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public long getForum() {
		return this.forum;
	}

	public void setForum(final Long forum) {
		this.forum = forum;
	}

	public Long getLastUpdater() {
		return this.lastUpdater;
	}

	public void setLastUpdater(final Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

}
