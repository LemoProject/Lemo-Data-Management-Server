package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.service.EResourceType;

@XmlRootElement
public class ResourceRequestInfo{

	private Long id;
	//private EResourceType resourcetype;
	private String resourcetype;
	private Long requests;
	private String title;
	private Long resolutionSlot;
	
	public Long getResolutionSlot() {
		return resolutionSlot;
	}

	public void setResolutionSlot(Long resolutionSlot) {
		this.resolutionSlot = resolutionSlot;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourcetype() {
		return resourcetype;
	}

//	public void setResourcetype(EResourceType resourcetype) {
//	this.resourcetype = resourcetype.toString();
//}

	
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
	
	public ResourceRequestInfo(Long id, EResourceType resourceType, Long requests, String title, Long resolutionSlot)
	{
		this.id = id;
		this.resourcetype = resourceType.toString();
		this.requests = requests;
		this.title = title;
		this.resolutionSlot = resolutionSlot;
	}


	
	
}
