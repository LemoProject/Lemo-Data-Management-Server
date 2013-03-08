/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayerPK.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for TQtiTestPlayerPK.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestPlayerPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333399944075685332L;
	private Long candidate;
	private Long container;
	private Long content;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiTestPlayerPK)) {
			return false;
		}
		final TQtiTestPlayerPK a = (TQtiTestPlayerPK) arg;
		if (a.getCandidate() != this.candidate) {
			return false;
		}
		if (a.getContainer() != this.container) {
			return false;
		}
		if (a.getContent() != this.content) {
			return false;
		}
		return true;
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

	@Override
	public int hashCode()
	{
		return (this.candidate.hashCode() * 17) + (this.container.hashCode() * 19) + (this.content.hashCode() * 23);
	}

	public TQtiTestPlayerPK()
	{

	}


}
