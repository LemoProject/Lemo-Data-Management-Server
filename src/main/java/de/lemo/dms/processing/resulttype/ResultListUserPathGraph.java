/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserPathGraph.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserPathGraph {

	private ArrayList<UserPathLink> links;
	private ArrayList<UserPathNode> nodes;

	@XmlElement
	public final String type = this.getClass().getSimpleName();

	public ResultListUserPathGraph() {
	}

	public ResultListUserPathGraph(final ArrayList<UserPathNode> nodes, final ArrayList<UserPathLink> links)
	{
		this.nodes = nodes;
		this.links = links;
	}

	@XmlElement
	public ArrayList<UserPathLink> getLinks()
	{
		return this.links;
	}

	public void setLinks(final ArrayList<UserPathLink> links)
	{
		this.links = links;
	}

	@XmlElement
	public ArrayList<UserPathNode> getNodes() {
		return this.nodes;
	}

	public void setNodes(final ArrayList<UserPathNode> nodes) {
		this.nodes = nodes;
	}
}
