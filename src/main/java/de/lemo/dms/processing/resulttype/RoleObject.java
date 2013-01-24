/**
 * File ./main/java/de/lemo/dms/processing/resulttype/RoleObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleObject {

	private Long id;
	private String name;

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public RoleObject()
	{

	}

	public RoleObject(final Long id, final String name)
	{
		this.id = id;
		this.name = name;
	}

	@XmlElement
	public Long getId() {
		return this.id;
	}

	@XmlElement
	public String getName() {
		return this.name;
	}

}
