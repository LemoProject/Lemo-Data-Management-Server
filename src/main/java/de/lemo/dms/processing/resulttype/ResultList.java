/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultList.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for any type which is use to transfer data from
 * the dms to the app-server
 * @author Leonard Kappe
 *
 */
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
