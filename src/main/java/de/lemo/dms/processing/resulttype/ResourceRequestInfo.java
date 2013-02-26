/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResourceRequestInfo.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;
import de.lemo.dms.processing.ELearningObjectType;

/**
 * represents a list for RequestInfo object's which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResourceRequestInfo {

	private Long id;
	private String resourcetype;
	private Long requests;
	private Long users;
	private String title;
	private Long resolutionSlot;

	public Long getUsers() {
		return this.users;
	}

	public void setUsers(final Long users) {
		this.users = users;
	}

	public Long getResolutionSlot() {
		return this.resolutionSlot;
	}

	public void setResolutionSlot(final Long resolutionSlot) {
		this.resolutionSlot = resolutionSlot;
	}

	public void setResourcetype(final String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getResourcetype() {
		return this.resourcetype;
	}

	public void setResourcetype(final ELearningObjectType resourcetype) {
		this.resourcetype = resourcetype.toString();
	}

	public Long getRequests() {
		return this.requests;
	}

	public void setRequests(final Long requests) {
		this.requests = requests;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public ResourceRequestInfo()
	{
	}

	public void incRequests()
	{
		this.requests++;
	}

	public ResourceRequestInfo(final Long id, final ELearningObjectType resourceType, final Long requests,
			final Long users, final String title, final Long resolutionSlot)
	{
		this.id = id;
		this.resourcetype = resourceType.toString();
		this.requests = requests;
		this.title = title;
		this.resolutionSlot = resolutionSlot;
		this.users = users;
	}

}
