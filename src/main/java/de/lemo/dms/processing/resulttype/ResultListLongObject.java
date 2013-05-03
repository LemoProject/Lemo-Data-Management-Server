/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListLongObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for Long objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListLongObject {

	private List<Long> elements;

	public ResultListLongObject()
	{

	}

	public ResultListLongObject(final List<Long> elements)
	{
		this.elements = elements;
	}

	@XmlElement
	public List<Long> getElements()
	{
		return this.elements;
	}

}
