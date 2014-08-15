/**
 * File ./src/main/java/de/lemo/dms/core/LongTupelHelper.java
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
 * File ./main/java/de/lemo/dms/core/LongTupelHelper.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

/**
 * helper class for Long tupel values
 * @author Boris Wenzlaff
 *
 */
public class LongTupelHelper {

	private Long id;
	private Long value = 1L;

	public void setValue(final Long value)
	{
		this.value = value;
	}

	public Long getId() {
		return this.id;
	}

	public Long getValue() {
		return this.value;
	}

	public LongTupelHelper()
	{

	}

	public LongTupelHelper(final Long id)
	{
		this.id = id;
	}

	public LongTupelHelper(final Long id, final long value)
	{
		this.id = id;
		this.value = value;
	}

	public void incValue()
	{
		this.value++;
	}

}
