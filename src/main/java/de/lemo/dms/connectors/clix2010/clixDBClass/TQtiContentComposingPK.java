/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContentComposingPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContentComposingPK.java
 * Date 2013-03-06
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for TQtiContentComposing.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class TQtiContentComposingPK implements Serializable {

	private static final long serialVersionUID = -4802836843072674394L;
	private Long candidate_id;
	private Long content_id;
	private Long container_id;
	
	public Long getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(Long candidate_id) {
		this.candidate_id = candidate_id;
	}

	public Long getContent_id() {
		return content_id;
	}

	public void setContent_id(Long content_id) {
		this.content_id = content_id;
	}

	public Long getContainer_id() {
		return container_id;
	}

	public void setContainer_id(Long container_id) {
		this.container_id = container_id;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiContentComposingPK)) {
			return false;
		}
		final TQtiContentComposingPK a = (TQtiContentComposingPK) arg;
		if (a.getCandidate_id() != this.candidate_id) {
			return false;
		}
		if (a.getContent_id() != this.content_id) {
			return false;
		}
		if (a.getContainer_id() != this.container_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content_id.hashCode() * 17) + (this.container_id.hashCode() * 19) + (this.candidate_id.hashCode() * 23);
	}
}
