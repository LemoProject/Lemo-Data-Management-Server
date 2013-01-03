package de.lemo.dms.db.miningDBclass;
import java.sql.Timestamp;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table for configuration information of the extraction tool.*/
public class ConfigMining implements IMappingClass{
	private Timestamp lastmodified;
	private long extractcycle;
	private long elapsed_time;
	private Long platform;
	private String databaseModel;

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}


	public ConfigMining() {
		this.lastmodified = new Timestamp(0);
	}
	
	/** standard getter for the attribute lastmodified
	 * @return the timestamp when the mining db was updated the last time
	 */
//	public long getLastmodified() {
//		return lastmodified.getTime();
//	}
	
	public Timestamp getLastmodified() {
		return lastmodified;
	}
	/** standard setter for the attribute lastmodified
	 * @param  lastmodified the timestamp of the most recent db-update
	 */
	public void setLastmodified(long lastmodified) {
			this.lastmodified.setTime(lastmodified);
	}
	public void setLastmodified(Timestamp lastmodified) {
		this.lastmodified = lastmodified;
}
	/** standard getter for the attribute extractcycle
	 * @return when starting updates cyclic this will be the interval
	 */	

	public long getExtractcycle() {
		return extractcycle;
	}
	/** standard setter for the attribute extractcycle
	 * @param 
	 */	
	public void setExtractcycle(long extractcycle) {
		this.extractcycle = extractcycle;
	}
	/** standard getter for the attribute elapsed_time
	 * @return the time the last update needed to run
	 */	
//	public long getElapsed_time() {
//		return elapsed_time.getTime();
//	}
	public long getElapsed_time() {
		return elapsed_time;
	}
	/** standard setter for the attribute elapsed_time
	 * @param elapsed_time the time the last update needed to run
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

	@Override
	public long getId() {
		return lastmodified.getTime();
	}

	@Override
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ConfigMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ConfigMining))
			return true;
		return false;
	}
}
