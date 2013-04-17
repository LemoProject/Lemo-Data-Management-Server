/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayerRespPK.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for TQtiTestPlayerResp.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestPlayerRespPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333399944075685332L;
	private Long candidate;
	private Long container;
	private Long content;
	private Long response;
	private Long subitemPosition;
	private Long testItem;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiTestPlayerRespPK)) {
			return false;
		}
		final TQtiTestPlayerRespPK a = (TQtiTestPlayerRespPK) arg;
		if (a.getCandidate() != this.candidate) {
			return false;
		}
		if (a.getContainer() != this.container) {
			return false;
		}
		if (a.getContent() != this.content) {
			return false;
		}
		if (a.getResponse() != this.response) {
			return false;
		}
		if (a.getSubitemPosition() != this.subitemPosition) {
			return false;
		}
		if (a.getTestItem() != this.testItem) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.candidate.hashCode() * 17) + (this.container.hashCode() * 19) + (this.content.hashCode() * 23)
				+ (this.response.hashCode() * 29) + (this.subitemPosition.hashCode() * 31) + (this.testItem.hashCode() * 37);
	}

	public TQtiTestPlayerRespPK()
	{

	}

	
	public Long getCandidate() {
		return candidate;
	}

	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}

	
	public Long getContainer() {
		return container;
	}

	
	public void setContainer(Long container) {
		this.container = container;
	}

	
	public Long getContent() {
		return content;
	}

	
	public void setContent(Long content) {
		this.content = content;
	}

	
	public Long getResponse() {
		return response;
	}

	
	public void setResponse(Long response) {
		this.response = response;
	}

	
	public Long getSubitemPosition() {
		return subitemPosition;
	}

	
	public void setSubitemPosition(Long subitemPosition) {
		this.subitemPosition = subitemPosition;
	}

	
	public Long getTestItem() {
		return testItem;
	}

	
	public void setTestItem(Long testItem) {
		this.testItem = testItem;
	}
}
