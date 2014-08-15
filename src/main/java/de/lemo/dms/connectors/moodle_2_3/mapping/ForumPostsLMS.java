/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/mapping/ForumPostsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/mapping/Forum_posts_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table ForumPosts.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_forum_posts")
public class ForumPostsLMS {

	private long id;
	private long userid;
	private long created;
	private long modified;
	private String subject;
	private String message;
	private long discussion;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="userid")
	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	@Column(name="created")
	public long getCreated() {
		return this.created;
	}

	public void setCreated(final long created) {
		this.created = created;
	}

	@Column(name="modified")
	public long getModified() {
		return this.modified;
	}

	public void setModified(final long modified) {
		this.modified = modified;
	}

	@Column(name="subject")
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@Column(name="message")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Column(name="discussion")
	public long getDiscussion() {
		return discussion;
	}

	public void setDiscussion(long discussion) {
		this.discussion = discussion;
	}
}
