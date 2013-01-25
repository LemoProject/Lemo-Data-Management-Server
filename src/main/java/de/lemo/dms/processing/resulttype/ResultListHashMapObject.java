/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListHashMapObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListHashMapObject {

	private HashMap<Long, ResultListLongObject> elements;
	private List<ResultListLongObject> entries;
	private Long[] keys;

	public ResultListHashMapObject()
	{

	}

	public ResultListHashMapObject(final HashMap<Long, ResultListLongObject> elements)
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

	public HashMap<Long, ResultListLongObject> getElements()
	{
		this.elements = new HashMap<Long, ResultListLongObject>();
		if ((this.keys != null) && (this.entries != null) && (this.keys.length == this.entries.size())) {
			for (int i = 0; i < this.keys.length; i++) {
				this.elements.put(this.keys[i], this.entries.get(i));
			}
		} else {
			System.out.println("ResultListHashMap ---  Empty Resultset !!!");
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
		this.keys = keys;
	}
}
