/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table BiTrackContentImpressions.
 * 
 * @author S.Schwarzrock
 *
 */

@Entity
@Table(name = "BI_TRACKCONTENT_IMPRESSIONS")
public class BiTrackContentImpressions implements IClixMappingClass {

	
	private BiTrackContentImpressionsPK id;

	private Long content;
	private String dayOfAccess;
	private Long container;
	private Long user;
	private Long totalImpressions;
	private Long characteristic;

	@EmbeddedId
	public BiTrackContentImpressionsPK getId() {
		return this.id;
	}

	public void setId(final BiTrackContentImpressionsPK id) {
		this.id = id;
	}

	@Column(name="CONTENT_ID")
	public Long getContent() {
		return this.content;
	}

	public void setContent(final Long content) {
		this.content = content;
	}

	@Column(name="CHARACTERISTIC_ID")
	public Long getCharacteristic() {
		return this.characteristic;
	}

	public void setCharacteristic(final Long characteristic) {
		this.characteristic = characteristic;
	}

	public BiTrackContentImpressions()
	{

	}

	@Column(name="DAY_OF_ACCESS")
	public String getDayOfAccess() {
		return this.dayOfAccess;
	}

	public void setDayOfAccess(final String dayOfAccess) {
		this.dayOfAccess = dayOfAccess;
	}

	@Column(name="CONTAINER_ID")
	public long getContainer() {
		return this.container;
	}

	public void setContainer(final Long container) {
		this.container = container;
	}

	@Column(name="USER_ID")
	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	@Column(name="TOTAL_IMPRESSIONS")
	public Long getTotalImpressions() {
		return this.totalImpressions;
	}

	public void setTotalImpressions(final long totalImpressions) {
		this.totalImpressions = totalImpressions;
	}

}
