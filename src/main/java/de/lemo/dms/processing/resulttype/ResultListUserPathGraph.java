package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserPathGraph {

    private ArrayList<UserPathLink> links;
    private ArrayList<UserPathNode> nodes;

    @XmlElement
    public final String type = getClass().getSimpleName();

    public ResultListUserPathGraph() {
    }

    public ResultListUserPathGraph(ArrayList<UserPathNode> nodes, ArrayList<UserPathLink> links)
    {
        this.nodes = nodes;
        this.links = links;
    }

    @XmlElement
    public ArrayList<UserPathLink> getLinks()
    {
        return this.links;
    }

    public void setLinks(ArrayList<UserPathLink> links)
    {
        this.links = links;
    }

    @XmlElement
    public ArrayList<UserPathNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<UserPathNode> nodes) {
        this.nodes = nodes;
    }
}
