/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListBoxPlot.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for BoxPlot objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListBoxPlot {

	private List<BoxPlot> elements;

	public ResultListBoxPlot() {

	}

	public ResultListBoxPlot(final List<BoxPlot> elements) {
		this.elements = elements;
	}

	@XmlElement
	public List<BoxPlot> getElements() {
		return this.elements;
	}

}