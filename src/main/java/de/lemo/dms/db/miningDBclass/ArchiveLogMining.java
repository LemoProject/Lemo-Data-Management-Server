/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/ArchiveLogMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/ArchiveLogMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */package de.lemo.dms.db.miningDBclass;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the table archive_log_mining. 
 * @author Sebastian Schwarzrock
 */
public class ArchiveLogMining implements ILogMining {

	private long id;
	private ArchiveMining archive;
	private UserMining user;
	private CourseMining course;
	private long timestamp;
	private long platform;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public ArchiveMining getArchive() {
		return archive;
	}
	
	public void setArchive(ArchiveMining archive) {
		this.archive = archive;
	}
	
	public UserMining getUser() {
		return user;
	}
	
	public void setUser(UserMining user) {
		this.user = user;
	}
	
	public CourseMining getCourse() {
		return course;
	}
	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getPlatform() {
		return platform;
	}

	public void setPlatform(long platform) {
		this.platform = platform;
	}
	
	@Override
	public int compareTo(final ILogMining arg0) {
		ILogMining s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof ArchiveLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ArchiveLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}


	@Override
	public String getAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		return this.archive.getTitle();
	}

	@Override
	public Long getLearnObjId() {
		return this.archive.getId();
	}

	@Override
	public Long getDuration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDuration(Long duration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
