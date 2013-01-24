/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressionsPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class BiTrackContentImpressionsPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194079312809898488L;
	private Long content;
	private String dayOfAccess;
	private Long container;
	private Long user;
	private Long characteristic;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof BiTrackContentImpressionsPK)) {
			return false;
		}
		final BiTrackContentImpressionsPK a = (BiTrackContentImpressionsPK) arg;
		if (a.getUser() != this.user) {
			return false;
		}
		if (a.getDayOfAccess() != this.dayOfAccess) {
			return false;
		}
		if (a.getContainer() != this.container) {
			return false;
		}
		if (a.getContent() != this.content) {
			return false;
		}
		if (a.getCharacteristic() != this.characteristic) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content.hashCode() * 17) + (this.characteristic.hashCode() * 19)
				+ (this.container.hashCode() * 23) + (this.dayOfAccess.hashCode() * 29) + (this.user.hashCode() * 31);
	}

	public long getContent() {
		return this.content;
	}

	public void setContent(final long content) {
		this.content = content;
	}

	public long getCharacteristic() {
		return this.characteristic;
	}

	public void setCharacteristic(final long characteristic) {
		this.characteristic = characteristic;
	}

	public BiTrackContentImpressionsPK(final long characteristic, final long content, final String dayOfAccess,
			final long container, final long user)
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
		return this.dayOfAccess;
	}

	public void setDayOfAccess(final String dayOfAccess) {
		this.dayOfAccess = dayOfAccess;
	}

	public long getContainer() {
		return this.container;
	}

	public void setContainer(final long container) {
		this.container = container;
	}

	public long getUser() {
		return this.user;
	}

	public void setUser(final long user) {
		this.user = user;
	}
}
