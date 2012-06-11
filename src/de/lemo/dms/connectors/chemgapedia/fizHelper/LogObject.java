package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.util.Iterator;
import java.util.List;

import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.UserMining;

// TODO: Auto-generated Javadoc
/**
 * The Class LogObject.
 */
public class LogObject implements Comparable<LogObject>{

	/** The id. */
	private long id;
	
	/** The time. */
	private long time;
	
	/** The url. */
	private String url;
	
	/** The referrer. */
	private String referrer;
	
	/** The status. */
	private String status;
	
	private long duration;
	
	private CourseMining course;
	
	private UserMining user;
	
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

	public boolean equals(LogObject log)
	{
		try{
		if(log == null)
			return false;
		LogObject l = (LogObject)log;
		if(l.getId() == this.getId())
			return true;
		}catch(Exception e)
		{
			return false;
		}
		return false;
	}

	/**
	 * Creates a new log object.
	 */
	public LogObject(){}
	
	/**
	 * Creates a new log object.
	 *
	 * @param id the id
	 * @param status the status
	 * @param time the time
	 * @param url the url
	 * @param referrer the referrer
	 */
	public LogObject(long id, String status, long time, String url, String referrer)
	{
		this.id = id;
		this.status = status;
		this.time = time;
		this.url = url;
		this.referrer = referrer;
	}
	
	public LogObject(long id)
	{
		this.id = id;
	}
	
	
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * Gets the HTML status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the HTML status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the referrer.
	 *
	 * @return the referrer
	 */
	public String getReferrer() {
		return referrer;
	}

	/**
	 * Sets the referrer.
	 *
	 * @param referrer the new referrer
	 */
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public void setId(String id,  List<IDMappingMining> id_mapping_mining) {
		long id_n = idForHash(id, id_mapping_mining);
		this.id = id_n;
	}
	
	public static long idForHash(String hash1, List<IDMappingMining> id_mapping_mining)
	{
		long id_n = -1;
		for(Iterator<IDMappingMining> iter = id_mapping_mining.iterator(); iter.hasNext();)
		{
			IDMappingMining loadedItem = iter.next();
			if(loadedItem.getHash() == hash1)
				id_n = loadedItem.getId();
		}		
		return id_n;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int compareTo(LogObject o) {
		LogObject s;
		try{
			s = (LogObject)o;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.time > s.getTime())
			return 1;
		if(this.time < s.getTime())
			return -1;
		return 0;
	}
	
	
	
}
