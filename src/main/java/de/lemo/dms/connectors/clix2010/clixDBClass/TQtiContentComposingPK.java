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

/**
 * Class for realization of primary key for TQtiContentComposing.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiContentComposingPK implements Serializable {

	private static final long serialVersionUID = -4802836843072674394L;
	private Long candidate;
	private Long content;
	private Long container;
	
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
		if (a.getCandidate() != this.candidate) {
			return false;
		}
		if (a.getContent() != this.content) {
			return false;
		}
		if (a.getContainer() != this.container) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content.hashCode() * 17) + (this.container.hashCode() * 19) + (this.candidate.hashCode() * 23);
	}
	
	public Long getContainer() {
		return container;
	}
	
	public void setContainer(Long container) {
		this.container = container;
	}
	
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	
}
