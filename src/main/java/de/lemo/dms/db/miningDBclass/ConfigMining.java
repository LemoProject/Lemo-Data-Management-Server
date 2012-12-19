package de.lemo.dms.db.miningDBclass;
import java.sql.Timestamp;

/** This class represents the table for configuration information of the extraction tool.*/
public class ConfigMining {
	private Timestamp lastmodified;
	private long extractcycle;
	private long elapsed_time;
	private long largestId;
	private Long platform;
	private String databaseModel;

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	public long getLargestId() {
		return largestId;
	}

	public void setLargestId(long largestId) {
		this.largestId = largestId;
	}

	public ConfigMining() {
		this.lastmodified = new Timestamp(0);
	}
	
	/** standard getter for the attribut lastmodified
	 * @return the timestamp when the mining db was updated the last time
	 */
//	public long getLastmodified() {
//		return lastmodified.getTime();
//	}
	
	public Timestamp getLastmodified() {
		return lastmodified;
	}
	/** standard setter for the attribut lastmodified
	 * @param  lastmodified the timestamp when the mining db was updated the last time
	 */
	public void setLastmodified(long lastmodified) {
			this.lastmodified.setTime(lastmodified);
	}
	public void setLastmodified(Timestamp lastmodified) {
		this.lastmodified = lastmodified;
}
	/** standard getter for the attribut extractcycle
	 * @return when starting updates cyclical this will be the intervall
	 */	
//	public long getExtractcycle() {
//		return extractcycle.getTime();
//	}
	public long getExtractcycle() {
		return extractcycle;
	}
	/** standard setter for the attribut extractcycle
	 * @param extractcycle when starting updates cyclical this will be the intervall
	 */	
	public void setExtractcycle(long extractcycle) {
		this.extractcycle = extractcycle;
	}
	/** standard getter for the attribut elapsed_time
	 * @return the timespan which the last update needs to run
	 */	
//	public long getElapsed_time() {
//		return elapsed_time.getTime();
//	}
	public long getElapsed_time() {
		return elapsed_time;
	}
	/** standard setter for the attribut elapsed_time
	 * @param elapsed_time the timespan which the last update needs to run
	 */	
	public void setElapsed_time(long elapsed_time) {
		this.elapsed_time = elapsed_time;
	}

	public String getDatabaseModel() {
		return databaseModel;
	}

	public void setDatabaseModel(String databaseModel) {
		this.databaseModel = databaseModel;
	}
}
