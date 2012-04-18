package de.lemo.dms.service.servicecontainer;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * service container for time formats as long
 * @author Boris Wenzlaff
 *
 */
@XmlRootElement
public class SCTime {
	private long time;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
