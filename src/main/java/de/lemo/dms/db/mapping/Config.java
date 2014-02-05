/**
 * File ./src/main/java/de/lemo/dms/db/mapping/ConfigMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/ConfigMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/** 
 * This class represents the table for configuration information of the extraction tool.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "config")
public class Config implements IMappingClass {

	private Timestamp lastModified;
	private long extractCycle;
	private long elapsedTime;
	private Long platform;
	private String databaseModel;
	private long latestTimestamp;

	@Column	(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	public Config() {
		this.lastModified = new Timestamp(0);
	}

	/**
	 * standard getter for the attribute lastModified
	 * 
	 * @return the timestamp when the mining db was updated the last time
	 */
	@Id
	@Column	(name="lastmodified")
	public Timestamp getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(final Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public void setLastModifiedLong(final long lastModified) {
		this.lastModified.setTime(lastModified);
	}

	/**
	 * standard getter for the attribute extractCycle
	 * 
	 * @return when starting updates cyclic this will be the interval
	 */

	@Column	(name="extractcycle")
	public long getExtractCycle() {
		return this.extractCycle;
	}

	/**
	 * standard setter for the attribute extractCycle
	 * 
	 * @param
	 */
	public void setExtractCycle(final long extractcycle) {
		this.extractCycle = extractcycle;
	}

	/**
	 * standard getter for the attribute elapsed_time
	 * 
	 * @return the time the last update needed to run
	 */
	@Column	(name="elapsed_time")
	public long getElapsedTime() {
		return this.elapsedTime;
	}

	/**
	 * standard setter for the attribute elapsedTime
	 * 
	 * @param elapsedTime
	 *            the time the last update needed to run
	 */
	public void setElapsedTime(final long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Column	(name="database_model")
	public String getDatabaseModel() {
		return this.databaseModel;
	}

	public void setDatabaseModel(final String databaseModel) {
		this.databaseModel = databaseModel;
	}

	@Override

	public long getId() {
		return this.lastModified.getTime();
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof Config)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Config)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) lastModified.getTime();
	}

	@Column	(name="latest_timestamp")
	public long getLatestTimestamp() {
		return latestTimestamp;
	}

	public void setLatestTimestamp(long latestTimestamp) {
		this.latestTimestamp = latestTimestamp;
	}
	
	public void setId(long id)
	{
		
	}
}
