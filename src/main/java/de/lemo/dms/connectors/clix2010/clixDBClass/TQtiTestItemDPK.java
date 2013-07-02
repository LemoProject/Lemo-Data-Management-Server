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

/**
 * Class for realization of primary key for TQtiTestItemDPK.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestItemDPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802836843072674394L;
	private Long content;
	private Long language;

	
	public Long getContent() {
		return content;
	}

	
	public void setContent(Long content) {
		this.content = content;
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
		if (a.getContent() != this.content) {
			return false;
		}
		if (a.getLanguage() != this.language) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content.hashCode() * 17) + (this.language.hashCode() * 19);
	}

	public TQtiTestItemDPK()
	{
	}

	public Long getLanguage() {
		return language;
	}

	public void setLanguage(Long language) {
		this.language = language;
	}
}
