package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserPathGraph {

    private ArrayList<UserPathEdge> edges;
    private ArrayList<UserPathNode> nodes;

    @XmlElement
    public final String type = getClass().getSimpleName();

    public ResultListUserPathGraph() {
    }

    public ResultListUserPathGraph(ArrayList<UserPathNode> nodes, ArrayList<UserPathEdge> edges)
    {
        this.nodes = nodes;
        this.edges = edges;
    }

    @XmlElement
    public ArrayList<UserPathEdge> getEdges()
    {
        return this.edges;
    }

    public void setEdges(ArrayList<UserPathEdge> edges)
    {
        this.edges = edges;
    }

    @XmlElement
    public ArrayList<UserPathNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<UserPathNode> nodes) {
        this.nodes = nodes;
    }
}
