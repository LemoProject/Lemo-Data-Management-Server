package de.lemo.dms.processing.resulttype;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserInstance {


	private List<UserInstance> elements;

	public ResultListUserInstance() {

	}

	public ResultListUserInstance(final List<UserInstance> elements) {
		this.elements = elements;
	}

	@XmlElement
	public List<UserInstance> getElements() {
		return this.elements;
	}

}

