package de.lemo.dms.processing.resulttype;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONObject;


@XmlRootElement
public class ResultListUserInstance {


	private List<UserInstance> elements;
	private String classifier;

	public ResultListUserInstance() {

	}

	public ResultListUserInstance(final List<UserInstance> elements) {
		this.setElements(elements);
	}

	@XmlElement
	public List<UserInstance> getElements() {
		return this.elements;
	}
	
	@XmlElement
	public String getClassifier() {
		return this.classifier;
	}

	public void setElements(List<UserInstance> elements) {
		this.elements = elements;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

}

