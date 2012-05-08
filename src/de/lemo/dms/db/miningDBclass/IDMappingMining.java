package de.lemo.dms.db.miningDBclass;


public class IDMappingMining {

	private Long id;
	private String hash;
	private String platform;
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public IDMappingMining()
	{
		
	}
	
	public IDMappingMining(long id, String hash)
	{
		this.id = id;
		this.hash = hash;
		this.platform = "Unknown";
	}
	
	public IDMappingMining(long id, String hash, String platform)
	{
		this.id = id;
		this.hash = hash;
		this.platform = platform;
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
