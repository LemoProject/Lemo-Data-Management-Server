package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathObject {
	
	private String id;
	private String title;
	private Long weight;
	private List<String> edges;
	private Double duration;
	private Long group;
	private String type;
	
	
	
	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	@XmlElement
	public List<String> getEdges() {
		return new ArrayList<String>(edges);
	}

	public void setEdges(List<String> edges) {
		this.edges = edges;
	}

	@XmlElement
	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public UserPathObject()
	{
		
	}
	
	public UserPathObject(String id, String title, Long weight, String type, Double duration, Long group)
	{
		this.id = id;
		this.title = title;
		this.weight = weight;
		this.group = group;
		this.edges = new ArrayList<String>();
		this.type = type;
		this.duration = duration / weight;
		
		
	}
	
	public void addEdge(String target)
	{
		this.edges.add(target);
	}
	
	public void increaseWeight(Double duration)
	{
		this.weight++;
		this.duration = (this.duration + duration) / weight;
	}

}
