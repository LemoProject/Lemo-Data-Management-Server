package de.lemo.dms.processing.resulttype;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONObject;


@XmlRootElement
public class ResultListUserInstance {


	private List<UserInstance> elements;
	private String classifier;
	private String validation;

	public ResultListUserInstance() {

	}

	public ResultListUserInstance(final List<UserInstance> elements) {
		this.setElements(elements);
		this.setClassifier("{\"nodes\":[{\"name\":\"ProgressPercentage\",\"value\":4097},{\"name\":\"failed (4010.0)\",\"value\":\"4010\"},{\"name\":\"passed (87.0)\",\"value\":\"87\"}],\"links\":[{\"source\":0,\"target\":1,\"name\":\"<= 78\",\"value\":4010},{\"source\":0,\"target\":2,\"name\":\"> 78\",\"value\":87}]}");
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

	public void setValidation(String createValidation) {
		this.validation = createValidation;
	}
	@XmlElement
	public String getValidation() {
		return validation;		
	}
}

