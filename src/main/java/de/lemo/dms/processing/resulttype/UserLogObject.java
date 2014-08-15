/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/UserLogObject.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/UserLogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a log from a user as object
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class UserLogObject implements Comparable<UserLogObject> {

	private Long userId;
	private Long timestamp;
	private String title;
	private Long objectId;
	private Long course;
	private String type;
	private String info;

	@XmlElement
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@XmlElement
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@XmlElement
	public Long getGroup() {
		return this.course;
	}

	public void setGroup(final Long group) {
		this.course = group;
	}

	public UserLogObject()
	{

	}

	public UserLogObject(final Long userId, final long timestamp, final String title, final Long objectId,
			final String type, final Long course, final String info)
	{
		this.userId = userId;
		this.timestamp = timestamp;
		this.objectId = objectId;
		this.title = title;
		this.course = course;
		this.type = type;
		this.setInfo(info);

	}

	@XmlElement
	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	@XmlElement
	public Long getObjectId() {
		return this.objectId;
	}

	public void setObjectId(final Long objectId) {
		this.objectId = objectId;
	}

	@XmlElement
	public String getInfo() {
		return this.info;
	}

	public void setInfo(final String info) {
		this.info = info;
	}

	@Override
	public int compareTo(final UserLogObject arg0) {
		UserLogObject s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (this.getUserId() > s.getUserId()) {
			return 1;
		}
		if (this.getUserId() < s.getUserId()) {
			return -1;
		}
		if (this.getUserId() == s.getUserId())
		{
			if (this.getTimestamp() > s.getTimestamp()) {
				return 1;
			}
			if (this.getTimestamp() < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@XmlElement
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

}
