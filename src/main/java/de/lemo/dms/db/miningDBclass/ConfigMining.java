/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ConfigMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.sql.Timestamp;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the table for configuration information of the extraction tool.
 * @author Sebastian Schwarzrock
 */
public class ConfigMining implements IMappingClass {

	private Timestamp lastModified;
	private long extractCycle;
	private long elapsedTime;
	private Long platform;
	private String databaseModel;
	private long latestTimestamp;

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	public ConfigMining() {
		this.lastModified = new Timestamp(0);
	}

	/**
	 * standard getter for the attribute lastModified
	 * 
	 * @return the timestamp when the mining db was updated the last time
	 */
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
		if (!(o instanceof ConfigMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ConfigMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) lastModified.getTime();
	}

	public long getLatestTimestamp() {
		return latestTimestamp;
	}

	public void setLatestTimestamp(long latestTimestamp) {
		this.latestTimestamp = latestTimestamp;
	}
}
