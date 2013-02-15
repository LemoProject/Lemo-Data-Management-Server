/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComposing.
 * 
 * @author S.Schwarzrock
 *
 */
public class EComposing implements IClixMappingClass {

	private Long id;
	private Long composing;
	private Long component;
	private Long parent;
	private String endDate;
	private String startDate;
	private Long composingType;

	public Long getComposingType() {
		return this.composingType;
	}

	public void setComposingType(final Long composingType) {
		this.composingType = composingType;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	public EComposing()
	{

	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getComposing() {
		return this.composing;
	}

	public void setComposing(final Long composing) {
		this.composing = composing;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getParent() {
		return this.parent;
	}

	public void setParent(final Long parent) {
		this.parent = parent;
	}

}
