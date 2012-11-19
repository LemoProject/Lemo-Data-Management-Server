package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class ForumEntry  implements IClixMappingClass{

	private Long id;
	private Long forum;
	private Long lastUpdater;
	private String lastUpdated;
	private String title;
	private String content;
	
	public ForumEntry()
	{}
	
	public String getString()
	{
		return "ForumEntry$$$"
				+this.id+"$$$"
				+this.getContent()+"$$$"
				+this.getForum()+"$$$"
				+this.getLastUpdated()+"$$$"
				+this.getTitle()+"$$$"
				+this.getLastUpdater();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getForum() {
		return forum;
	}

	public void setForum(Long forum) {
		this.forum = forum;
	}

	public Long getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
