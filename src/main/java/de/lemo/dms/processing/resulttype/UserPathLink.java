/**
 * File ./main/java/de/lemo/dms/processing/resulttype/UserPathLink.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represent a link between two nodes which are objects that have been uses
 * by users
 * @author Boris Wenzlaff
 *
 */
@XmlRootElement
public class UserPathLink {

	private String source;
	private String target;
	private String value;

	private Long pathId;

	public Long getPathId() {
		return this.pathId;
	}

	public void setPathId(final Long pathId) {
		this.pathId = pathId;
	}

	public UserPathLink() {
		// TODO Auto-generated constructor stub
	}

	public UserPathLink(final String source, final String target) {
		this.source = source;
		this.target = target;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		final int NEXTNUMBER = 32;
		result = (PRIME * result) + (this.source.length() ^ (this.source.length() >>> NEXTNUMBER));
		result = (PRIME * result) + (this.target.length() ^ (this.target.length() >>> NEXTNUMBER));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final UserPathLink other = (UserPathLink) obj;
		if (this.source != other.source) {
			return false;
		}
		if (this.target != other.target) {
			return false;
		}
		return true;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
