package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ResultList {

	@XmlElement
	private List<?> elements;
	
	public ResultList()
	{
		
	}
	
	public ResultList(List<?> elements)
	{
		this.elements = elements;
	}
	
	public List<?> getElements()
	{
		return this.elements;
	}
	
}
