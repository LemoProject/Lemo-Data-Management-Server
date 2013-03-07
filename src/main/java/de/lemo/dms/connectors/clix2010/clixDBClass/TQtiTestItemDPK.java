/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemDPK.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for TQtiTestItemDPK.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestItemDPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802836843072674394L;
	private Long content;
	private Long language;

	
	public Long getContent() {
		return content;
	}

	
	public void setContent(Long content) {
		this.content = content;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TQtiTestItemDPK)) {
			return false;
		}
		final TQtiTestItemDPK a = (TQtiTestItemDPK) arg;
		if (a.getContent() != this.content) {
			return false;
		}
		if (a.getLanguage() != this.language) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.content.hashCode() * 17) + (this.language.hashCode() * 19);
	}

	public TQtiTestItemDPK()
	{
	}

	public Long getLanguage() {
		return language;
	}

	public void setLanguage(Long language) {
		this.language = language;
	}
}
