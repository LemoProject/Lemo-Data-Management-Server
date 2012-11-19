package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BoxPlot {
	private String name = "";
	private double median = 0, upperWhisker = 0, lowerWhisker = 0;
	//absolute zugriffe und einzigartige zugriffe
	private long upperQuartil = 0, lowerQuartil = 0;

	@XmlElement
	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	@XmlElement
	public double getUpperQuartil() {
		return upperQuartil;
	}

	public void setUpperQuartil(long upperQuartil) {
		this.upperQuartil = upperQuartil;
	}

	@XmlElement
	public double getLowerQuartil() {
		return lowerQuartil;
	}

	public void setLowerQuartil(long lowerQuartil) {
		this.lowerQuartil = lowerQuartil;
	}

	@XmlElement
	public double getUpperWhisker() {
		return upperWhisker;
	}

	public void setUpperWhisker(double upperWhisker) {
		this.upperWhisker = upperWhisker;
	}

	@XmlElement
	public double getLowerWhisker() {
		return lowerWhisker;
	}

	public void setLowerWhisker(double lowerWhisker) {
		this.lowerWhisker = lowerWhisker;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
