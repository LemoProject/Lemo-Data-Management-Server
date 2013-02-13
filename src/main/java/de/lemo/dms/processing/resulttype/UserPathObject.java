/**
 * File ./main/java/de/lemo/dms/processing/resulttype/UserPathObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for path object which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
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
		return this.pathId;
	}

	public Long getTotalUsers() {
		return this.totalUsers;
	}

	public void setTotalUsers(final Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Long getTotalRequests() {
		return this.totalRequests;
	}

	public void setTotalRequests(final Long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public void setPathId(final Long pathId) {
		this.pathId = pathId;
	}

	@XmlElement
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@XmlElement
	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@XmlElement
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@XmlElement
	public Long getWeight() {
		return this.weight;
	}

	public void setWeight(final Long weight) {
		this.weight = weight;
	}

	@XmlElement
	public HashMap<String, Integer> getEdges() {
		return this.edges;
	}

	public void setEdges(final HashMap<String, Integer> edges) {
		this.edges = edges;
	}

	/**
	 * @return the duration
	 */
	public Double getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(final Double duration) {
		this.duration = duration;
	}

	@XmlElement
	public Long getGroup() {
		return this.group;
	}

	public void setGroup(final Long group) {
		this.group = group;
	}

	public UserPathObject()
	{

	}

	public UserPathObject(final String id, final String title, final Long weight, final String type,
			final Double duration, final Long group, final Long pathId, final Long totalRequests, final Long totalUsers)
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

	public void addEdgeOrIncrement(final String target)
	{
		Integer edgeWeight = this.edges.get(target);
		if (edgeWeight == null) {
			edgeWeight = 0;
		}
		edgeWeight++;
		this.edges.put(target, edgeWeight);
	}

	public void increaseWeight(final Double duration)
	{
		this.weight++;
		this.duration = (this.duration + duration) / this.weight;
	}

}
