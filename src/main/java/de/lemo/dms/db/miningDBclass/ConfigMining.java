package de.lemo.dms.db.miningDBclass;
import java.sql.Timestamp;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table for configuration information of the extraction tool.*/
public class ConfigMining implements IMappingClass{
	private Timestamp lastModified;
	private long extractCycle;
	private long elapsedTime;
	private Long platform;
	private String databaseModel;

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}


	public ConfigMining() {
		this.lastModified = new Timestamp(0);
	}
	
	/** standard getter for the attribute lastModified
	 * @return the timestamp when the mining db was updated the last time
	 */
//	public long getLastmodified() {
//		return lastModified.getTime();
//	}
	
	public Timestamp getLastmodified() {
		return lastModified;
	}
	/** standard setter for the attribute lastModified
	 * @param  lastModified the timestamp of the most recent db-update
	 */
	public void setLastmodified(long lastModified) {
			this.lastModified.setTime(lastModified);
	}
	public void setLastmodified(Timestamp lastModified) {
		this.lastModified = lastModified;
}
	/** standard getter for the attribute extractCycle
	 * @return when starting updates cyclic this will be the interval
	 */	

	public long getExtractcycle() {
		return extractCycle;
	}
	/** standard setter for the attribute extractCycle
	 * @param 
	 */	
	public void setExtractcycle(long extractcycle) {
		this.extractCycle = extractcycle;
	}
	/** standard getter for the attribute elapsed_time
	 * @return the time the last update needed to run
	 */	
//	public long getElapsed_time() {
//		return elapsed_time.getTime();
//	}
	public long getElapsed_time() {
		return elapsedTime;
	}
	/** standard setter for the attribute elapsedTime
	 * @param elapsedTime the time the last update needed to run
	 */	
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getDatabaseModel() {
		return databaseModel;
	}

	public void setDatabaseModel(String databaseModel) {
		this.databaseModel = databaseModel;
	}

	@Override
	public long getId() {
		return lastModified.getTime();
	}

	@Override
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof ConfigMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ConfigMining))
			return true;
		return false;
	}
}
