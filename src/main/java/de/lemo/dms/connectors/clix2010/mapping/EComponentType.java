/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/EComponentType.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/EComponentType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComponentType.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "E_COMPONENTTYPE")
public class EComponentType implements IClixMappingClass {

	private Long id;
	private Long component;
	private Long characteristic;
	private Long language;
	private String uploadDir;
	private String lastUpdated;

	@Column(name="UPLOADDIRECTORY")
	public String getUploadDir() {
		return this.uploadDir;
	}

	public void setUploadDir(final String uploadDir) {
		this.uploadDir = uploadDir;
	}

	@Column(name="LANGUAGE_ID")
	public Long getLanguage() {
		return this.language;
	}

	public void setLanguage(final Long language) {
		this.language = language;
	}
	
	@Column(name="COMPONENT_ID")
	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	@Column(name="CHARACTERISTIC_ID")
	public Long getCharacteristicId() {
		return this.characteristic;
	}

	public void setCharacteristicId(final Long characteristicId) {
		this.characteristic = characteristicId;
	}

	@Id
	@Column(name="COMPONENTTYPE_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="LASTUPDATED")
	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
