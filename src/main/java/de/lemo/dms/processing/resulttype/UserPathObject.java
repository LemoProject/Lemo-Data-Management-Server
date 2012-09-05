package de.lemo.dms.processing.resulttype;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathObject {

    private String id;
    private String title;
    private Long weight;
    private HashMap<String, Integer> edges;
    private Double duration;
    private Long group;
    private String type;
    private Long pathId;
    private Long totalUsers;
    private Long totalRequests;
//
    public Long getPathId() {
		return pathId;
	}

	public Long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Long getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(Long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public void setPathId(Long pathId) {
		this.pathId = pathId;
	}

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
    public HashMap<String, Integer> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<String, Integer> edges) {
        this.edges = edges;
    }

    /**
	 * @return the duration
	 */
	public Double getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Double duration) {
		this.duration = duration;
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

    public UserPathObject(String id, String title, Long weight, String type, Double duration, Long group, Long pathId, Long totalRequests, Long totalUsers)
    {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.group = group;
        this.edges = new HashMap<String, Integer>();
        this.type = type;
        this.duration = duration / weight;
        this.pathId = pathId;
        this.totalRequests = totalRequests;
        this.totalUsers = totalUsers;

    }

    public void addEdgeOrIncrement(String target)
    {
        Integer edgeWeight = edges.get(target);
        if(edgeWeight == null) {
            edgeWeight = 0;
        }
        edgeWeight++;
        edges.put(target, edgeWeight);
    }

    public void increaseWeight(Double duration)
    {
        this.weight++;
        this.duration = (this.duration + duration) / weight;
    }

}
