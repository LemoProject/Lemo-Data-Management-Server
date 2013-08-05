/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressionsPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressionsPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for BiTrackContentImpressions.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class BiTrackContentImpressionsPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194079312809898488L;
	private Long content_id;
	private String day_of_access;
	private Long container_id;
	private Long user_id;
	private Long characteristic_id;

	public Long getContent_id() {
		return content_id;
	}

	public void setContent_id(Long content_id) {
		this.content_id = content_id;
	}

	public String getDay_Of_Access() {
		return day_of_access;
	}

	public void setDay_Of_Access(String dayOfAccess) {
		this.day_of_access = dayOfAccess;
	}

	public Long getContainer_id() {
		return container_id;
	}

	public void setContainer_id(Long container_id) {
		this.container_id = container_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getCharacteristic_id() {
		return characteristic_id;
	}

	public void setCharacteristic_id(Long characteristic_id) {
		this.characteristic_id = characteristic_id;
	}

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
		if (a.getUser_id() != this.user_id) {
			return false;
		}
		if (a.getDay_Of_Access() != this.day_of_access) {
			return false;
		}
		if (a.getContainer_id() != this.container_id) {
			return false;
		}
		if (a.getContent_id() != this.content_id) {
			return false;
		}
		if (a.getCharacteristic_id() != this.characteristic_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content_id.hashCode() * 17) + (this.characteristic_id.hashCode() * 19)
				+ (this.container_id.hashCode() * 23) + (this.day_of_access.hashCode() * 29) + (this.user_id.hashCode() * 31);
	}

	public BiTrackContentImpressionsPK(){
	}

	public BiTrackContentImpressionsPK(final long characteristic, final long content, final String dayOfAccess,
			final long container, final long user)
	{
		this.characteristic_id = characteristic;
		this.content_id = content;
		this.day_of_access = dayOfAccess;
		this.container_id = container;
		this.user_id = user;
	}

	
}
