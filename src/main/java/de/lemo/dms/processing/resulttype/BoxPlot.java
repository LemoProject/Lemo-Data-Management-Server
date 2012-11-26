package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BoxPlot {
	private String name;// = "";
	private Double 	median, // = 0.0, 
					upperWhisker,// = 0.0, 
					lowerWhisker;// = 0.0;
	//absolute zugriffe und einzigartige zugriffe
	private Double  upperQuartil,// = 0.0, 
					lowerQuartil;// = 0.0;

	@XmlElement
	public Double getMedian() {
		return median;
	}

	public void setMedian(Double median) {
		this.median = median;
	}

	@XmlElement
	public Double getUpperQuartil() {
		return upperQuartil;
	}

	public void setUpperQuartil(Double upperQuartil) {
		this.upperQuartil = upperQuartil;
	}

	@XmlElement
	public Double getLowerQuartil() {
		return lowerQuartil;
	}

	public void setLowerQuartil(Double lowerQuartil) {
		this.lowerQuartil = lowerQuartil;
	}

	@XmlElement
	public Double getUpperWhisker() {
		return upperWhisker;
	}

	public void setUpperWhisker(Double upperWhisker) {
		this.upperWhisker = upperWhisker;
	}

	@XmlElement
	public Double getLowerWhisker() {
		return lowerWhisker;
	}

	public void setLowerWhisker(Double lowerWhisker) {
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
