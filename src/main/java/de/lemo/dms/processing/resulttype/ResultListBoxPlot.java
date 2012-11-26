package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListBoxPlot {

	private List<BoxPlot> elements;

	public ResultListBoxPlot() {

	}

	public ResultListBoxPlot(List<BoxPlot> elements) {
		this.elements = elements;
	}

	@XmlElement
	public List<BoxPlot> getElements() {
		return this.elements;
	}

}