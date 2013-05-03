/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table BiTrackContentImpressions.
 * 
 * @author S.Schwarzrock
 *
 */
public class BiTrackContentImpressions implements IClixMappingClass {

	private BiTrackContentImpressionsPK id;

	private Long content;
	private String dayOfAccess;
	private Long container;
	private Long user;
	private Long totalImpressions;
	private Long characteristic;

	public BiTrackContentImpressionsPK getId() {
		return this.id;
	}

	public void setId(final BiTrackContentImpressionsPK id) {
		this.id = id;
	}

	public Long getContent() {
		return this.content;
	}

	public void setContent(final Long content) {
		this.content = content;
	}

	public Long getCharacteristic() {
		return this.characteristic;
	}

	public void setCharacteristic(final Long characteristic) {
		this.characteristic = characteristic;
	}

	public BiTrackContentImpressions()
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

	public void setContainer(final Long container) {
		this.container = container;
	}

	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	public Long getTotalImpressions() {
		return this.totalImpressions;
	}

	public void setTotalImpressions(final long totalImpressions) {
		this.totalImpressions = totalImpressions;
	}

}
