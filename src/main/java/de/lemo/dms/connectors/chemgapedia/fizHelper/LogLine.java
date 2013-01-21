package de.lemo.dms.connectors.chemgapedia.fizHelper;

import de.lemo.dms.connectors.Encoder;


/**
 * Class for parsing a line of a Log-file into its parts. Can be modified, if the Log-file format changes. 
 * @author s.schwarzrock
 *
 */
public class LogLine implements Comparable<LogLine>{
	
	/** Id, which is basically the users hashed IP-address. */
	private String id;
	
	/** URL, the user requested. */
	private String url;
	
	/** Referrer. */
	private String referrer;
	
	/** Timestamp. */
	private Long timestamp;
	
	/** HTTP status of the request. */
	private String httpStatus;
	
	/** The is valid. */
	private boolean isValid;
	
	@Override
	public int compareTo(LogLine o) {
		LogLine s;
		try{
			s = (LogLine)o;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.timestamp > s.getTimestamp())
			return 1;
		if(this.timestamp < s.getTimestamp())
			return -1;
		return 0;
	}
	
	/**
	 * Constructor of the class. Parses the given line from the server-log-line and puts the found values into the object's fields.
	 * 
	 * @param logLine single line taken from the server-log-file
	 */
	public LogLine(String logLine)
	{
		String[] lineArguments = logLine.split("\t");
		if(lineArguments.length > 5)
		{
			isValid = true;
			//Set id
			if(lineArguments[2].split("/").length == 2)
				id = Encoder.createMD5(lineArguments[2].split("/")[0].trim()) + "/" +  lineArguments[2].split("/")[1];	//IF Cookie is set use Cookie-ID
			else
				id = Encoder.createMD5(lineArguments[3].trim())+"";		//use IP else
			
			//Set timestamp
			timestamp = Long.parseLong(lineArguments[0]);
			
			//Set URL
			if(lineArguments[4].equals("-"))
				System.out.println();
			if(!lineArguments[4].trim().startsWith("http://www.chemgapedia.de"))
				url = "http://www.chemgapedia.de"+lineArguments[4].trim();
			else
				url = lineArguments[4].trim();
			if(this.url.contains("\\"))
				isValid = false;
			
			//Set HTTP-status
			httpStatus = lineArguments[1].trim();	
			
			//Set referrer
			if(!lineArguments[5].contains("www.google."))
				referrer = lineArguments[5].trim();
			else
				referrer = "-";	
		}
		else
			isValid = false;
	}
	
	
	/**
	 * Checks if the line is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * Gets the HTTP status.
	 *
	 * @return the HTTP status
	 */
	public String getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the HTTP status.
	 *
	 * @param httpStatus the new HTTP status
	 */
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the URL.
	 *
	 * @return the URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL.
	 *
	 * @param url the new URL
	 */
	public void setUrl(String url) {
		this.url = url;
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



}
