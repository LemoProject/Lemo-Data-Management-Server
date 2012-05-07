package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ResultListLongObject {

	
	private List<Long> elements;
	
	public ResultListLongObject()
	{
		
	}
	
	public ResultListLongObject(List<Long> elements)
	{
		this.elements = elements;
	}
	
	@XmlElement
	public List<Long> getElements()
	{
		return this.elements;
	}
	
}
