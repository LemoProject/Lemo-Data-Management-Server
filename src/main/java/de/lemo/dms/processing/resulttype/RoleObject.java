package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleObject {
	
	private Long id;
	private String name;
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    public RoleObject()
    {
    	
    }
    
    public RoleObject(Long id, String name)
    {
    	this.id = id;
    	this.name = name;
    }
    
    @XmlElement
	public Long getId() {
		return id;
	}
    
    @XmlElement
	public String getName() {
		return name;
	}
	
	

}
