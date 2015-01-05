/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/LevelAssociationLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/LevelAssociationLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represanting the link between hierarchy objects
 * @author Sebastian Schwarzrock
 *
 */
@Entity
@Table(name = "level_association")
public class LevelAssociationLMS{

	private long id;
	private long upper;
	private long lower;
	private Long platform;

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between levels
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between levels
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute upper
	 * 
	 * @return a level in which the resource is used
	 */
	@Column(name="upper_id")
	public long getUpper() {
		return this.upper;
	}

	/**
	 * standard setter for the attribute upper
	 * 
	 * @param upper
	 *            a level in which the resource is used
	 */
	public void setUpper(final long upper) {
		this.upper = upper;
	}

	/**
	 * standard getter for the attribute lower
	 * 
	 * @return the degree which is used in the department
	 */
	@Column(name="lower_id")
	public long getLower() {
		return this.lower;
	}

	/**
	 * standard setter for the attribute lower
	 * 
	 * @param degree
	 *            the degree which is used in the department
	 */
	public void setLower(final long lower) {
		this.lower = lower;
	}

	
	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
