package de.lemo.dms.db.miningDBclass;


public class IDMappingMining {

	private Long id;
	private String hash;
	
	public IDMappingMining()
	{
		
	}
	
	public IDMappingMining(long id, String hash)
	{
		this.id = id;
		this.hash = hash;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
