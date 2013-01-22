package de.lemo.dms.db.miningDBclass;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class PlatformMining implements IMappingClass{

	private Long id;
	private String name;
	private String type;
	private Long prefix;
	
	public PlatformMining(Long id, String name, String type, Long prefix)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.prefix = prefix;
	}
	
	public PlatformMining()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public boolean equals(IMappingClass o) {
		if(!(o instanceof PlatformMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof PlatformMining))
			return true;
		return false;
	}

	public Long getPrefix() {
		return prefix;
	}

	public void setPrefix(Long prefix) {
		this.prefix = prefix;
	}

}
