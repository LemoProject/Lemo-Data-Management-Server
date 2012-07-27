package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserPathGraphString {

    private ArrayList<UserPathLinkString> links;
    private ArrayList<UserPathNode> nodes;

    @XmlElement
    public final String type = getClass().getSimpleName();

    public ResultListUserPathGraphString() {
    }

    public ResultListUserPathGraphString(ArrayList<UserPathNode> nodes, ArrayList<UserPathLinkString> links)
    {
        this.nodes = nodes;
        this.links = links;
    }

    @XmlElement
    public ArrayList<UserPathLinkString> getLinks()
    {
        return this.links;
    }

    public void setLinks(ArrayList<UserPathLinkString> links)
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
