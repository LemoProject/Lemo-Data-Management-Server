/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListResourceRequestInfo.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for ResourceRequestInfo objects which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListResourceRequestInfo {

	private List<ResourceRequestInfo> rri;

	public ResultListResourceRequestInfo() {
		this.rri = new ArrayList<ResourceRequestInfo>();
	}

	public ResultListResourceRequestInfo(final List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}

	@XmlElement
	public List<ResourceRequestInfo> getResourceRequestInfos() {
		return this.rri;
	}

	public void setRoles(final List<ResourceRequestInfo> resourceRequestInfos) {
		this.rri = resourceRequestInfos;
	}

	public void add(final ResourceRequestInfo rri)
	{
		this.rri.add(rri);
	}

	public void addAll(final Collection<ResourceRequestInfo> rri)
	{
		this.rri.addAll(rri);
	}
}
