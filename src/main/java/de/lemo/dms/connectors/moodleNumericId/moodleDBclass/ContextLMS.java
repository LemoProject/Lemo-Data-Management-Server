/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Context_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Context.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ContextLMS {

	private long id;
	private long contextlevel;
	private long instanceid;
	private String path;
	private long depth;

	public long getDepth() {
		return this.depth;
	}

	public void setDepth(final long depth) {
		this.depth = depth;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setContextlevel(final long contextlevel) {
		this.contextlevel = contextlevel;
	}

	public long getContextlevel() {
		return this.contextlevel;
	}

	public void setInstanceid(final long instanceid) {
		this.instanceid = instanceid;
	}

	public long getInstanceid() {
		return this.instanceid;
	}
}
