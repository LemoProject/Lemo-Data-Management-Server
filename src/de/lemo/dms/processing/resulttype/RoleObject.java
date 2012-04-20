package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleObject {
	
    @XmlElement
	private Long id;
    @XmlElement
	private String name;
    
    public RoleObject()
    {
    	
    }
    
    public RoleObject(Long id, String name)
    {
    	this.id = id;
    	this.name = name;
    }
    
    
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	

}
