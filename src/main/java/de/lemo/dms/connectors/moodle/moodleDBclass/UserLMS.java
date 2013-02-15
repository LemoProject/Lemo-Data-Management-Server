/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/UserLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table User.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class UserLMS {

	private String id;
	private long firstaccess;
	private long lastaccess;
	private long lastlogin;
	private long currentlogin;
	private long timemodified;
	private String login;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public long getFirstaccess() {
		return this.firstaccess;
	}

	public void setFirstaccess(final long firstaccess) {
		this.firstaccess = firstaccess;
	}

	public long getLastaccess() {
		return this.lastaccess;
	}

	public void setLastaccess(final long lastaccess) {
		this.lastaccess = lastaccess;
	}

	public long getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(final long lastlogin) {
		this.lastlogin = lastlogin;
	}

	public long getCurrentlogin() {
		return this.currentlogin;
	}

	public void setCurrentlogin(final long currentlogin) {
		this.currentlogin = currentlogin;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}
}
