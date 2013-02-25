/**
 * File ./main/java/de/lemo/dms/service/servicecontainer/SCTime.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service.servicecontainer;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * service container for time formats as long
 * 
 * @author Boris Wenzlaff
 */
@XmlRootElement
public class SCTime {

	private long time;

	public long getTime() {
		return this.time;
	}

	public void setTime(final long time) {
		this.time = time;
	}

}
