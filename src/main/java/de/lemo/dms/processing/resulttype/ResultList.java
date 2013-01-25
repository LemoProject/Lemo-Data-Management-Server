/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultList.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultList {

	@XmlElement
	private List<?> elements;

	public ResultList()
	{

	}

	public ResultList(final List<?> elements)
	{
		this.elements = elements;
	}

	public List<?> getElements()
	{
		return this.elements;
	}

}
