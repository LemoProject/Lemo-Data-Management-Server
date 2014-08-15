/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/ResultListHashMapObject.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListHashMapObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;

/**
 * represents a list for HashMapObject's which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListHashMapObject {

	private Map<Long, ResultListLongObject> elements;
	private List<ResultListLongObject> entries;
	private Long[] keys;
	private Logger logger = Logger.getLogger(this.getClass());

	public ResultListHashMapObject()
	{

	}

	public ResultListHashMapObject(final Map<Long, ResultListLongObject> elements)
	{
		this.elements = elements;

		if (elements != null) {
			final Set<Long> keys = elements.keySet();
			this.keys = keys.toArray(new Long[keys.size()]);
			this.entries = new ArrayList<ResultListLongObject>();
			final Iterator<Long> it = keys.iterator();
			while (it.hasNext()) {
				final Long courseID = it.next();
				this.entries.add(elements.get(courseID));
			}
		}
	}

	public Map<Long, ResultListLongObject> getElements()
	{
		this.elements = new HashMap<Long, ResultListLongObject>();
		if ((this.keys != null) && (this.entries != null) && (this.keys.length == this.entries.size())) {
			for (int i = 0; i < this.keys.length; i++) {
				this.elements.put(this.keys[i], this.entries.get(i));
			}
		} else {
			logger.warn("ResultListHashMap mpty Resultset");
		}

		return this.elements;
	}

	@XmlElement
	public List<ResultListLongObject> getEntries() {
		return this.entries;
	}

	public void setEntries(final List<ResultListLongObject> entries) {
		this.entries = entries;
	}

	@XmlElement
	public Long[] getKeys() {
		return this.keys;
	}

	public void setKeys(final Long[] keys) {
		this.keys = (Long[]) keys.clone();
	}
}
