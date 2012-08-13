package de.lemo.dms.core;

public class LongTupelHelper {
	
	
	private Long id;
	private Long value = 1L;
	
	public void setValue(Long value)
	{
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}

	public Long getValue() {
		return value;
	}

	public LongTupelHelper()
	{
		
	}
	
	public LongTupelHelper(Long id)
	{
		this.id = id;
	}
	
	public LongTupelHelper(Long id, long value)
	{
		this.id = id;
		this.value = value;
	}
	
	public void incValue()
	{
		this.value++;
	}
	
	

}
