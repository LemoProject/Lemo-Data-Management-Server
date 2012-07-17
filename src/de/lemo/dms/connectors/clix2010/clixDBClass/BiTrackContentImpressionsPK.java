package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class BiTrackContentImpressionsPK implements Serializable{
	
	private Long content;
	private String dayOfAccess;
	private Long container;
	private Long user;
	private Long characteristic;
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof BiTrackContentImpressionsPK))
			return false;
		BiTrackContentImpressionsPK a = (BiTrackContentImpressionsPK)arg;
		if(a.getUser() != this.user)
			return false;
		if(a.getDayOfAccess() != this.dayOfAccess)
			return false;
		if(a.getContainer() != this.container)
			return false;
		if(a.getContent() != this.content)
			return false;
		if(a.getCharacteristic() != this.characteristic)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		return content.hashCode() * 17 + characteristic.hashCode() * 19 + container.hashCode() * 23 + dayOfAccess.hashCode() * 29 + user.hashCode() * 31;
	}
	
	public long getContent() {
		return content;
	}

	public void setContent(long content) {
		this.content = content;
	}

	public long getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(long characteristic) {
		this.characteristic = characteristic;
	}
	
	public BiTrackContentImpressionsPK(long characteristic, long content, String dayOfAccess, long container, long user)
	{
		this.characteristic = characteristic;
		this.content = content;
		this.dayOfAccess = dayOfAccess;
		this.container = container;
		this.user = user;
	}

	public BiTrackContentImpressionsPK()
	{
		
	}

	public String getDayOfAccess() {
		return dayOfAccess;
	}

	public void setDayOfAccess(String dayOfAccess) {
		this.dayOfAccess = dayOfAccess;
	}

	public long getContainer() {
		return container;
	}

	public void setContainer(long container) {
		this.container = container;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}
}
