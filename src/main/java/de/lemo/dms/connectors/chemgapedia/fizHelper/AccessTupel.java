package de.lemo.dms.connectors.chemgapedia.fizHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessTupel.
 */
public class AccessTupel {

	
	/** The id. */
	private String id = "";


	/** The count. */
	private long count = 1;
	
	/**
	 * Creates a new access tupel.
	 */
	public AccessTupel()
	{
		
	}
	
	/**
	 * Creates a new access tupel.
	 *
	 * @param id the id
	 * @param count the count
	 */
	public AccessTupel(String id, long count)
	{
		this.id = id;
		this.count = count;
	}
	
	/**
	 * Creates a new access tupel.
	 *
	 * @param id the id
	 */
	public AccessTupel(String id)
	{
		this.id = id;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(long count) {
		this.count = count;
	}
	
	/**
	 * Inc count.
	 */
	public void incCount()
	{
		count++;
	}
	
	
}
