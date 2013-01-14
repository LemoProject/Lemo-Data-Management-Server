package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListStringObject {

    private List<String> elements;

    public ResultListStringObject()
    {

    }

    public ResultListStringObject(List<String> elements)
    {
        this.elements = elements;
    }

    @XmlElement
    public List<String> getElements()
    {
        return this.elements;
    }

}
