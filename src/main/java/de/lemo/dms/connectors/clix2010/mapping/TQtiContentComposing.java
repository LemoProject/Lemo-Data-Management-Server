/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/TQtiContentComposing.java
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
 * File ./main/java/de/lemo/dms/connectors/mapping/clixDBClass/EComposing.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComposing.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "T_QTI_CONTENTCOMPOSING")
public class TQtiContentComposing implements IClixMappingClass {

	private TQtiContentComposingPK id;
	private Long candidate;
	private Long content;
	private Long container;
	private Long position;
	
	
	@Column(name="CONTAINER_ID")
	public Long getContainer() {
		return container;
	}
	
	public void setContainer(Long container) {
		this.container = container;
	}
	
	@Column(name="CONTENT_ID")
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	@Column(name="CANDIDATE_ID")
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	@EmbeddedId
	public TQtiContentComposingPK getId() {
		return id;
	}
	
	public void setId(TQtiContentComposingPK id) {
		this.id = id;
	}

	@Column(name="POSITION_ID")
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}
	
	
}
