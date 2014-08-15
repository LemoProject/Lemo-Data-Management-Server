/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/UserPathObject.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/processing/resulttype/UserPathObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.HashMap;
import java.util.Map;
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
	private Map<String, Integer> edges;
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
	public Map<String, Integer> getEdges() {
		return this.edges;
	}

	public void setEdges(final Map<String, Integer> edges) {
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
