/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/fizHelper/LogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.util.Iterator;
import java.util.List;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * The Class LogObject. An object of this class holds the information of a single line in a log-file.
 */
public class LogObject implements Comparable<LogObject> {

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

	/** The duration of the access. */
	private long duration;

	/** The course. */
	private CourseMining course;

	/** The accessing user. */
	private UserMining user;

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * Gets the course.
	 * 
	 * @return the course
	 */
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * Sets the course.
	 * 
	 * @param course
	 *            the new course
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * Equals.
	 * 
	 * @param log
	 *            the log
	 * @return true, if successful
	 */
	public boolean equals(final LogObject log)
	{
		try {
			if (log == null) {
				return false;
			}
			final LogObject l = log;
			if (l.getId() == this.getId()) {
				return true;
			}
		} catch (final Exception e)
		{
			return false;
		}
		return false;
	}

	/**
	 * Constructor. Creates a new log object.
	 */
	public LogObject() {
	}

	/**
	 * Creates a new log object.
	 * 
	 * @param id
	 *            the id
	 * @param status
	 *            the status
	 * @param time
	 *            the time
	 * @param url
	 *            the url
	 * @param referrer
	 *            the referrer
	 */
	public LogObject(final long id, final String status, final long time, final String url, final String referrer)
	{
		this.id = id;
		this.status = status;
		this.time = time;
		this.url = url;
		this.referrer = referrer;
	}

	/**
	 * Instantiates a new log object.
	 * 
	 * @param id
	 *            the id
	 */
	public LogObject(final long id)
	{
		this.id = id;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public long getDuration() {
		return this.duration;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(final long duration) {
		this.duration = duration;
	}

	/**
	 * Gets the HTML status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Sets the HTML status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Gets the referrer.
	 * 
	 * @return the referrer
	 */
	public String getReferrer() {
		return this.referrer;
	}

	/**
	 * Sets the referrer.
	 * 
	 * @param referrer
	 *            the new referrer
	 */
	public void setReferrer(final String referrer) {
		this.referrer = referrer;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id
	 * @param id_mapping_mining
	 *            the id_mapping_mining
	 */
	public void setId(final String id, final List<IDMappingMining> id_mapping_mining) {
		final long id_n = LogObject.idForHash(id, id_mapping_mining);
		this.id = id_n;
	}

	/**
	 * Id for hash.
	 * 
	 * @param hash1
	 *            the hash1
	 * @param id_mapping_mining
	 *            the id_mapping_mining
	 * @return the long
	 */
	public static long idForHash(final String hash1, final List<IDMappingMining> id_mapping_mining)
	{
		long id_n = -1;
		for (final Iterator<IDMappingMining> iter = id_mapping_mining.iterator(); iter.hasNext();)
		{
			final IDMappingMining loadedItem = iter.next();
			if (loadedItem.getHash() == hash1) {
				id_n = loadedItem.getId();
			}
		}
		return id_n;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Sets the time.
	 * 
	 * @param time
	 *            the new time
	 */
	public void setTime(final long time) {
		this.time = time;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public int compareTo(final LogObject o) {
		LogObject s;
		try {
			s = o;
		} catch (final Exception e)
		{
			return 0;
		}
		if (this.time > s.getTime()) {
			return 1;
		}
		if (this.time < s.getTime()) {
			return -1;
		}
		return 0;
	}

}
