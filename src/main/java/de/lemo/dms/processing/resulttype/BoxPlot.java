/**
 * File ./main/java/de/lemo/dms/processing/resulttype/BoxPlot.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for one boxplot element
 * @author Boris Wenzlaff
 *
 */
@XmlRootElement
public class BoxPlot {

	private String name;
	private Double median;
	private Double 	upperWhisker;
	private Double 	lowerWhisker;
	// absolute zugriffe und einzigartige zugriffe
	private Double upperQuartil;
	private Double 	lowerQuartil;

	@XmlElement
	public Double getMedian() {
		return this.median;
	}

	public void setMedian(final Double median) {
		this.median = median;
	}

	@XmlElement
	public Double getUpperQuartil() {
		return this.upperQuartil;
	}

	public void setUpperQuartil(final Double upperQuartil) {
		this.upperQuartil = upperQuartil;
	}

	@XmlElement
	public Double getLowerQuartil() {
		return this.lowerQuartil;
	}

	public void setLowerQuartil(final Double lowerQuartil) {
		this.lowerQuartil = lowerQuartil;
	}

	@XmlElement
	public Double getUpperWhisker() {
		return this.upperWhisker;
	}

	public void setUpperWhisker(final Double upperWhisker) {
		this.upperWhisker = upperWhisker;
	}

	@XmlElement
	public Double getLowerWhisker() {
		return this.lowerWhisker;
	}

	public void setLowerWhisker(final Double lowerWhisker) {
		this.lowerWhisker = lowerWhisker;
	}

	@XmlElement
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
