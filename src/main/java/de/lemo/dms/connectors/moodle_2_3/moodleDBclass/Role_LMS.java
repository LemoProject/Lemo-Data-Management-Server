package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Role_LMS {
	
	private long id;
	private String name;
	private String shortname;
	private String description;//text
	private long sortorder;
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getSortorder() {
		return sortorder;
	}
	public void setSortorder(long sortorder) {
		this.sortorder = sortorder;
	}
	
}
