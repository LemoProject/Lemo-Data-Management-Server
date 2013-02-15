/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComponentTypePK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for BiTrackEComponentType.
 * 
 * @author S.Schwarzrock
 *
 */
public class EComponentTypePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2147746468879800061L;
	private Long component;
	private Long language;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof EComponentTypePK)) {
			return false;
		}
		final EComponentTypePK a = (EComponentTypePK) arg;
		if (a.getComponent() != this.component) {
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
		int hc;
		hc = this.language.hashCode();
		hc = (17 * hc) + this.component.hashCode();
		return hc;
	}

	public long getLanguage() {
		return this.language;
	}

	public void setLanguage(final long language) {
		this.language = language;
	}

	public EComponentTypePK()
	{

	}

	public long getComponent() {
		return this.component;
	}

	public void setComponent(final long component) {
		this.component = component;
	}

}
