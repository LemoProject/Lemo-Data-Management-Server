package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathEdge {

    private int weight;
    private String source;
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
