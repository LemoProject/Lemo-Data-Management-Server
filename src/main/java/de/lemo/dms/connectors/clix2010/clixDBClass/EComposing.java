/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComposing.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "E_COMPOSING")
public class EComposing implements IClixMappingClass {

	private Long id;
	private Long composing;
	private Long component;
	private Long parent;
	private String endDate;
	private String startDate;
	private Long composingType;
	private String lastUpdated;

	@Column(name="COMPOSINGTYPE_ID")
	public Long getComposingType() {
		return this.composingType;
	}

	public void setComposingType(final Long composingType) {
		this.composingType = composingType;
	}

	@Column(name="STARTDATE")
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	@Column(name="ENDDATE")
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	@Id
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name="COMPOSING_ID")
	public Long getComposing() {
		return this.composing;
	}

	public void setComposing(final Long composing) {
		this.composing = composing;
	}

	@Column(name="COMPONENT_ID")
	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	@Column(name="PARENT_ID")
	public Long getParent() {
		return this.parent;
	}

	public void setParent(final Long parent) {
		this.parent = parent;
	}

	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


}
