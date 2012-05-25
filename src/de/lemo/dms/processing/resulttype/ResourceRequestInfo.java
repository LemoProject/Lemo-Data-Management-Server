package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.service.EResourceType;

@XmlRootElement
public class ResourceRequestInfo{

	private Long id;
	private EResourceType resourcetype;
	private Long requests;
	private String title;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EResourceType getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(EResourceType resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Long getRequests() {
		return requests;
	}

	public void setRequests(Long requests) {
		this.requests = requests;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ResourceRequestInfo()
	{
	}
	
	public void incRequests()
	{
		this.requests++;
	}
	
	public ResourceRequestInfo(Long id, EResourceType resourceType, Long requests, String title)
	{
		this.id = id;
		this.resourcetype = resourceType;
		this.requests = requests;
		this.title = title;
	}


	
	
}
