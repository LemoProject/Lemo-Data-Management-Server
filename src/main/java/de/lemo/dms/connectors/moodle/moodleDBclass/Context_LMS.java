package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Context_LMS {
	
	private long id;
	private long contextlevel;
	private long instanceid;
	private String path;
	private long depth;

	public long getDepth() {
		return depth;
	}
	public void setDepth(long depth) {
		this.depth = depth;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setContextlevel(long contextlevel) {
		this.contextlevel = contextlevel;
	}
	public long getContextlevel() {
		return contextlevel;
	}
	public void setInstanceid(long instanceid) {
		this.instanceid = instanceid;
	}
	public long getInstanceid() {
		return instanceid;
	}		
}
