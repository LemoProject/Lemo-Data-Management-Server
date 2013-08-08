/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/TQtiTestPlayerRespPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/TQtiTestPlayerRespPK.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for TQtiTestPlayerResp.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class TQtiTestPlayerRespPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333399944075685332L;
	private Long candidate_id;
	private Long container_id;
	private Long content_id;
	
	public Long getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(Long candidate_id) {
		this.candidate_id = candidate_id;
	}

	public Long getContainer_id() {
		return container_id;
	}

	public void setContainer_id(Long container_id) {
		this.container_id = container_id;
	}

	public Long getContent_id() {
		return content_id;
	}

	public void setContent_id(Long content_id) {
		this.content_id = content_id;
	}

	public Long getResponse_id() {
		return response_id;
	}

	public void setResponse_id(Long response_id) {
		this.response_id = response_id;
	}

	public Long getSubitem_position() {
		return subitem_position;
	}

	public void setSubitem_position(Long subitem_position) {
		this.subitem_position = subitem_position;
	}

	public Long getTestItem_id() {
		return testItem_id;
	}

	public void setTestItem_id(Long testItem_id) {
		this.testItem_id = testItem_id;
	}

	private Long response_id;
	private Long subitem_position;
	private Long testItem_id;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiTestPlayerRespPK)) {
			return false;
		}
		final TQtiTestPlayerRespPK a = (TQtiTestPlayerRespPK) arg;
		if (a.getCandidate_id() != this.candidate_id) {
			return false;
		}
		if (a.getContainer_id() != this.container_id) {
			return false;
		}
		if (a.getContent_id() != this.content_id) {
			return false;
		}
		if (a.getResponse_id() != this.response_id) {
			return false;
		}
		if (a.getSubitem_position() != this.subitem_position) {
			return false;
		}
		if (a.getTestItem_id() != this.testItem_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.candidate_id.hashCode() * 17) + (this.container_id.hashCode() * 19) + (this.content_id.hashCode() * 23)
				+ (this.response_id.hashCode() * 29) + (this.subitem_position.hashCode() * 31) + (this.testItem_id.hashCode() * 37);
	}

	public TQtiTestPlayerRespPK()
	{

	}

	
	
}
