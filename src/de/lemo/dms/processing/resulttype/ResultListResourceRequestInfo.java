package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListResourceRequestInfo {

	
	private List<ResourceRequestInfo> rri;

	public ResultListResourceRequestInfo() {
		this.rri = new ArrayList<ResourceRequestInfo>();
	}
	
	public ResultListResourceRequestInfo(List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}
	
	@XmlElement
	public List<ResourceRequestInfo> getResourceRequestInfos() {
		return rri;
	}

	public void setRoles(List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}
	
	public void add(ResourceRequestInfo rri)
	{
		this.rri.add(rri);
	}
	
	public void addAll(Collection<ResourceRequestInfo> rri)
	{
		this.rri.addAll(rri);
	}
}
