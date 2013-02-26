/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserPathGraph.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for PathGraph objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListUserPathGraph {

	private List<UserPathLink> links;
	private List<UserPathNode> nodes;

	@XmlElement
	public final String type = this.getClass().getSimpleName();

	public ResultListUserPathGraph() {
	}

	public ResultListUserPathGraph(final List<UserPathNode> nodes, final List<UserPathLink> links)
	{
		this.nodes = nodes;
		this.links = links;
	}

	@XmlElement
	public List<UserPathLink> getLinks()
	{
		return this.links;
	}

	public void setLinks(final List<UserPathLink> links)
	{
		this.links = links;
	}

	@XmlElement
	public List<UserPathNode> getNodes() {
		return this.nodes;
	}

	public void setNodes(final List<UserPathNode> nodes) {
		this.nodes = nodes;
	}
}
