/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListStringObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for String object which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListStringObject {

	private List<String> elements;

	public ResultListStringObject()
	{

	}

	public ResultListStringObject(final List<String> elements)
	{
		this.elements = elements;
	}

	@XmlElement
	public List<String> getElements()
	{
		return this.elements;
	}

}
