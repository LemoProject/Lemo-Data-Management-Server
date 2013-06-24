/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/ForumPostsLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table ForumPosts.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ForumPostsLMS {

	private long id;
	private String userid;
	private long created;
	private long modified;
	private String subject;
	private String message;
	private long discussion;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public long getCreated() {
		return this.created;
	}

	public void setCreated(final long created) {
		this.created = created;
	}

	public long getModified() {
		return this.modified;
	}

	public void setModified(final long modified) {
		this.modified = modified;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public long getDiscussion() {
		return discussion;
	}

	public void setDiscussion(long discussion) {
		this.discussion = discussion;
	}
}
