/**
 * File ./main/java/de/lemo/dms/core/LongTupelHelper.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.core;

public class LongTupelHelper {

	private Long id;
	private Long value = 1L;

	public void setValue(final Long value)
	{
		this.value = value;
	}

	public Long getId() {
		return this.id;
	}

	public Long getValue() {
		return this.value;
	}

	public LongTupelHelper()
	{

	}

	public LongTupelHelper(final Long id)
	{
		this.id = id;
	}

	public LongTupelHelper(final Long id, final long value)
	{
		this.id = id;
		this.value = value;
	}

	public void incValue()
	{
		this.value++;
	}

}
