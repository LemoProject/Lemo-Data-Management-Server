package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.List;

public class UserPathObject {
	
	private String id;
	private String title;
	private Long weight;
	private List<String> edges;
	private Long group;
	private String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public List<String> getEdges() {
		return edges;
	}

	public void setEdges(List<String> edges) {
		this.edges = edges;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public UserPathObject()
	{
		
	}
	
	public UserPathObject(String id, String title, Long weight, String type, Long group)
	{
		this.id = id;
		this.title = title;
		this.weight = weight;
		this.group = group;
		this.edges = new ArrayList<String>();
		this.type = type;
		
	}
	
	public void addEdge(String target)
	{
		this.edges.add(target);
	}
	
	public void increaseWeight()
	{
		this.weight++;
	}
	
	

}
