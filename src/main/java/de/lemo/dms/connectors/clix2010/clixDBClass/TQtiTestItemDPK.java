/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemDPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemDPK.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for TQtiTestItemDPK.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class TQtiTestItemDPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802836843072674394L;
	private Long content_id;
	private Long language_id;

	
	public Long getContent_id() {
		return content_id;
	}

	public void setContent_id(Long content_id) {
		this.content_id = content_id;
	}

	public Long getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiTestItemDPK)) {
			return false;
		}
		final TQtiTestItemDPK a = (TQtiTestItemDPK) arg;
		if (a.getContent_id() != this.content_id) {
			return false;
		}
		if (a.getLanguage_id() != this.language_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content_id.hashCode() * 17) + (this.language_id.hashCode() * 19);
	}

	public TQtiTestItemDPK()
	{
	}

}
